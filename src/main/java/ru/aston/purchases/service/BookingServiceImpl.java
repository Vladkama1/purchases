package ru.aston.purchases.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.purchases.dto.BookingDto;
import ru.aston.purchases.dto.BookingOutDto;
import ru.aston.purchases.dto.ItemOutDto;
import ru.aston.purchases.dto.UserOutDto;
import ru.aston.purchases.enums.State;
import ru.aston.purchases.exception.BadRequestException;
import ru.aston.purchases.exception.NotFoundException;
import ru.aston.purchases.exception.UnsupportedStatus;
import ru.aston.purchases.mapper.BookingMapper;
import ru.aston.purchases.mapper.ItemMapper;
import ru.aston.purchases.mapper.UserMapper;
import ru.aston.purchases.model.Booking;
import ru.aston.purchases.repository.BookingRepository;
import ru.aston.purchases.repository.ItemRepository;
import ru.aston.purchases.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.aston.purchases.enums.Status.*;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    @Override
    public BookingOutDto findById(Long userId, Long bookingId) {
        return bookingMapper.toOutDTO(bookingRepository.findBookingByIdAndBooker_IdOrIdAndItem_Owner_Id(bookingId, userId, bookingId, userId)
                .orElseThrow(() -> new NotFoundException("Заказа не существует по id: " + bookingId)));
    }

    @Override
    @Transactional
    public BookingOutDto saveBooking(Long userId, BookingDto bookingDto) {
        validDate(bookingDto);
        ItemOutDto itemFromDb = itemMapper.toOutDTO(itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Вещь отсутствует в базе!")));
        UserOutDto userFromDb = userMapper.toOutDTO(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь отсутствует в базе! " + userId)));
        if (itemFromDb.getAvailable().equals(false)) {
            throw new BadRequestException("Вещь " + bookingDto.getItemId() + " недоступна");
        }
        if (itemRepository.existsByIdAndOwner_Id(bookingDto.getItemId(), userId)) {
            throw new NotFoundException("Нельзя бронировать свою вещь");
        }
        bookingDto.setBookerId(userId);
        bookingDto.setStatus(WAITING);
        BookingOutDto bookingOutDto = bookingMapper.toOutDTO(bookingRepository.save(bookingMapper.toModel(bookingDto)));
        bookingOutDto.setItem(itemFromDb);
        bookingOutDto.setBooker(userFromDb);

        return bookingOutDto;
    }


    @Override
    @Transactional
    public BookingOutDto updateBooking(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь " + bookingId + " не найдена"));
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotFoundException("Пользователь " + userId + " не может изменять бронь " + bookingId);
        }
        if (approved.equals(true)) {
            if (booking.getStatus().equals(APPROVED)) {
                throw new BadRequestException("Уже одобрено");
            }
            booking.setStatus(APPROVED);
        } else if (approved.equals(false)) {
            if (booking.getStatus().equals(REJECTED)) {
                throw new BadRequestException("Уже отменено");
            }
            booking.setStatus(REJECTED);
        }
        return bookingMapper.toOutDTO(bookingRepository.save(booking));
    }

    @Override
    public List<BookingOutDto> findByBookerAndState(Long userId, String state, Integer from, Integer size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь " + userId + " не найден"));

        State bookingState;
        try {
            bookingState = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedStatus("Unknown state: UNSUPPORTED_STATUS");
        }

        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = PageRequest.of(from / size, size);

        switch (bookingState) {
            case ALL:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByBookerIdOrderByEndDesc(userId, pageable).getContent());
            case CURRENT:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByBookerIdAndStartLessThanEqualAndEndGreaterThanEqualOrderByEndDesc(userId, now, now, pageable).getContent());
            case PAST:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByBookerIdAndEndLessThanOrderByEndDesc(userId, now, pageable).getContent());
            case FUTURE:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByBookerIdAndStartGreaterThanOrderByEndDesc(userId, now, pageable).getContent());
            case WAITING:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByBookerIdAndStatusOrderByEndDesc(userId, WAITING, pageable).getContent());
            case REJECTED:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByBookerIdAndStatusOrderByEndDesc(userId, REJECTED, pageable).getContent());
            default:
                throw new UnsupportedStatus("Unknown state: " + state);
        }
    }

    @Override
    public List<BookingOutDto> findByOwnerAndState(Long ownerId, String state, Integer from, Integer size) {
        userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь " + ownerId + " не найден"));

        State stateEnum;
        try {
            stateEnum = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedStatus("Unknown state: UNSUPPORTED_STATUS");
        }

        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = PageRequest.of(from / size, size);

        switch (stateEnum) {
            case ALL:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(ownerId, pageable).getContent());
            case CURRENT:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByItemOwnerIdAndStartLessThanEqualAndEndGreaterThanEqualOrderByStartDesc(ownerId, now, now, pageable).getContent());
            case PAST:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByItemOwnerIdAndEndLessThanOrderByStartDesc(ownerId, now, pageable).getContent());
            case FUTURE:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByItemOwnerIdAndStartGreaterThanOrderByStartDesc(ownerId, now, pageable).getContent());
            case WAITING:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(ownerId, WAITING, pageable).getContent());
            case REJECTED:
                return bookingMapper.toListOutDTO(bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(ownerId, REJECTED, pageable).getContent());
            default:
                throw new UnsupportedStatus("Unknown state: " + state);
        }
    }

    private static void validDate(BookingDto bookingDto) {
        if (bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new BadRequestException("Дата окончания бронирования не может быть раньше старта");
        } else if (bookingDto.getEnd().equals(bookingDto.getStart())) {
            throw new BadRequestException("Одинаковые даты");
        }
    }
}

package vilksp.returnedService.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import vilksp.returnedService.model.PaymentCase;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FakePaymentCaseRepository implements PaymentCaseRepository {

    private HashMap<Long, PaymentCase> map = new HashMap<>();
    private long count = 0L;


    @Override
    public void flush() {

    }

    @Override
    public <S extends PaymentCase> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends PaymentCase> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<PaymentCase> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PaymentCase getOne(Long aLong) {
        return null;
    }

    @Override
    public PaymentCase getById(Long aLong) {
        return null;
    }

    @Override
    public PaymentCase getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends PaymentCase> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PaymentCase> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends PaymentCase> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<PaymentCase> findAll() {
        return null;
    }

    @Override
    public List<PaymentCase> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public <S extends PaymentCase> S save(S entity) {
        map.put(entity.getReturnedPayment().getPaymentId(), entity);
        return entity;
    }

    @Override
    public Optional<PaymentCase> findById(Long aLong) {
        return Optional.of(map.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(PaymentCase entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends PaymentCase> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<PaymentCase> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<PaymentCase> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PaymentCase> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PaymentCase> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PaymentCase> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PaymentCase> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PaymentCase, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Long countOfActiveCases() {
        return map.entrySet()
                .stream()
                .filter(x -> x != null && !x.getValue().getReturnedPayment()
                        .isSolved())
                .count();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.adambh.util.functions;

import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author adam
 */
public interface Try<T> {
    
    
    public default <T> Try<T> of(TrySupplier<T> theSupplier) {
        
    }
    
    public default <T> Try<T> success(T theValue) {
        
    } 
    
    public default <T> Try<T> failure(Throwable theThrowable) {
        
    }
    
    
    /**
     * If the try is currently successful, applies the predicate to the value
     * held within the try, returning a successful try on true, or a failure try
     * on false with a PredicateFailedException. If the try is currently 
     * failure, returns the existing failure. If the predicate throws an 
     * exception, the failure try holds that specific exception.
     * 
     * @param <T> the type of the try
     * @param thePredicate the predicate to apply
     * @return successful try if currently successful and the predicate holds, 
     *         otherwise a failure try.
    **/
    public <T> Try<T> filter(TryPredicate<T> thePredicate);
    
    
    public <U> Try<U> map(TryFunction<? super T, ? extends U> theFunction);
    
    
    public <U> Try<U> flatMap(TryFunction<? super T, Try<U>> theFunction);
    
    
    public T recover(Function<? super Throwable, T> theFunction);
    
    
    public Try<T> recoverWith(TryFunction<? super Throwable, Try<T>> theFunction);
    

    public Optional<T> toOptional();
    
    
    public T get()
            throws Throwable;
    
    
    public T orElse(T theValue);
    
    
    public Try<T> orElseTry(TrySupplier<T> theSupplier);
    
    
    public boolean isSuccess();
    
    
    public <E extends Throwable> Try<T> onSuccess(TryConsumer<T,E> theConsumer)
            throws E;
    
    
    public Try<T> onSuccessTry(TryConsumer<T> theConsumer);
    
    
    public <E extends Throwable> Try<T> onFailure(TryConsumer<Throwable,E> theConsumer)
            throws E;


}

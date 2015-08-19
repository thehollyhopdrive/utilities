/**
 * Do whatever you want with this.
 *
 * @created: 19/08/2015
**/
package uk.co.adambh.util.functions;



import java.util.Objects;
import java.util.function.*;



/**
 * Provides a set of utility methods to make working with method references 
 * which throw checked exceptions a little less problematic.
 *
 * @author Adam Bromage-Hughes <thehollyhopdrive@gmail.com>
**/
public final class MethodReferences {
    
    

	// ************************************************************************\
	// Enums                                                                   *
	// ************************************************************************/
	
	
	// ************************************************************************\
	// Static Variables                                                        *
	// ************************************************************************/
	
	
	// ************************************************************************\
	// Instance Variables                                                      *
	// ************************************************************************/
	
	
	// ************************************************************************\
	// Constructors                                                            *
	// ************************************************************************/

	private MethodReferences() {
	}
	
	
	// ************************************************************************\
	// Public Methods                                                          *
	// ************************************************************************/
	
	
	// ************************************************************************\
	// Protected Methods                                                       *
	// ************************************************************************/
	
	
	// ************************************************************************\
	// Private Methods                                                         *
	// ************************************************************************/
	
	
	// ************************************************************************\
	// Inner Classes                                                           *
	// ************************************************************************/
	
    /**
     * Functional interface with a single method signature matching 
     * {@link Consumer#accept(Object)} but which additionally throws a 
     * checked exception.
    **/ 
	@FunctionalInterface
	public interface ConsumerWithException<T> {
		public void accept(T theValue)
				throws Exception;
	}
    
    
    /**
     * Functional interface with a single method signature matching 
     * {@link BiConsumer#accept(Object, Object)} but which 
     * additionally throws a checked exception.
    **/ 
	@FunctionalInterface
	public interface BiConsumerWithException<T, U> {
		public void accept(T theFirstValue, U theSecondValue)
				throws Exception;
	}

    
    /**
     * Functional interface with a single method signature matching 
     * {@link Function#apply(Object)} but which additionally throws a 
     * checked exception.
    **/
	@FunctionalInterface
	public interface FunctionWithException<T, R> {
		public R apply(T theValue)
				throws Exception;
	}

    
    /**
     * Functional interface with a single method signature matching 
     * {@link BiFunction#apply(Object, Object)} but which 
     * additionally throws a checked exception.
    **/
	@FunctionalInterface
	public interface BiFunctionWithException<T, U, R> {
		public R apply(T theFirstValue, U theSecondValue)
				throws Exception;
	}

    
    /**
     * Functional interface with a single method signature matching
     * {@link Supplier#get()} but which additionally throws a checked exception.
    **/
	@FunctionalInterface
	public interface SupplierWithException<T> {
		public T get()
				throws Exception;
	}
    
    
    /**
     * Functional interface with a single method signature matching
     * {@link Predicate#test(Object)} but which additionally throws
     * a checked exception.
    **/ 
    @FunctionalInterface
    public interface PredicateWithException<T> {
        public boolean test(T theValue)
                throws Exception;
    }
    
    
    /**
     * Functional interface with a single method signature matching
     * {@link BiPredicate#test(Object, Object)} but which 
     * additionally throws a checked exception.
    **/ 
    @FunctionalInterface
    public interface BiPredicateWithException<T, U> {
        public boolean test(T theFirstValue, U theSecondValue)
                throws Exception;
    }
	
	
	// ************************************************************************\
	// Static Methods                                                          *
	// ************************************************************************/

    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link Consumer#accept(Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception wrapped in a {@link RuntimeException}. This allows methods 
     * which previously could not be used as method references due to their 
     * checked exception to now be used.
     * 
     * @param <T> the type to be consumed.
     * @param theConsumer the consumer.
     * 
     * @return the wrapped consumer. 
     * 
     * @throws NullPointerException if the consumer supplied is null.
    **/
	public static <T> Consumer<T> unchecked(
                                ConsumerWithException<? super T> theConsumer) {
        Objects.requireNonNull(theConsumer);
		return (theValue) -> {
			try {
				theConsumer.accept(theValue);
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
    
    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link BiConsumer#accept(Object,Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception wrapped in a {@link RuntimeException}. This allows methods 
     * which previously could not be used as method references due to their 
     * checked exception to now be used.
     * 
     * @param <T> the first type to be consumed.
     * @param <U> the second type to be consumed.
     * @param theConsumer the consumer.
     * 
     * @return the wrapped consumer. 
     * 
     * @throws NullPointerException if the consumer supplied is null.
    **/
    public static <T, U> BiConsumer<T, U> unchecked(
                    BiConsumerWithException<? super T, ? super U> theConsumer) {
        Objects.requireNonNull(theConsumer);
		return (theFirstValue, theSecondValue) -> {
			try {
				theConsumer.accept(theFirstValue, theSecondValue);
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link Function#apply(Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception wrapped in a {@link RuntimeException}. This allows methods 
     * which previously could not be used as method references due to their 
     * checked exception to now be used.
     * 
     * @param <T> the type supplied to the function.
     * @param <R> the type returned by the function.
     * @param theFunction the function.
     * 
     * @return the wrapped function. 
     * 
     * @throws NullPointerException if the function supplied is null.
    **/
	public static <T, R> Function<T, R> unchecked(
                            FunctionWithException<? super T, R> theFunction) {
		Objects.requireNonNull(theFunction);
        return (theValue) -> {
			try {
				return theFunction.apply(theValue);
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link BiFunction#apply(Object,Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception wrapped in a {@link RuntimeException}. This allows methods 
     * which previously could not be used as method references due to their 
     * checked exception to now be used.
     * 
     * @param <T> the first type supplied to the function.
     * @param <U> the second type supplied to the function.
     * @param <R> the type returned by the function.
     * @param theFunction the function.
     * 
     * @return the wrapped function. 
     * 
     * @throws NullPointerException if the function supplied is null.
    **/
	public static <T, U, R> BiFunction<T, U, R> unchecked(
						BiFunctionWithException<? super T, U, R> theFunction) {
		Objects.requireNonNull(theFunction);
        return (theFirstValue, theSecondValue) -> {
			try {
				return theFunction.apply(theFirstValue, theSecondValue);
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link Supplier#get()} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception wrapped in a {@link RuntimeException}. This allows methods 
     * which previously could not be used as method references due to their 
     * checked exception to now be used.
     * 
     * @param <T> the type returned by the supplier.
     * @param theSupplier the supplier.
     * 
     * @return the wrapped supplier. 
     * 
     * @throws NullPointerException if the supplier supplied is null.
    **/
	public static <T> Supplier<T> unchecked(
                                        SupplierWithException<T> theSupplier) {
		Objects.requireNonNull(theSupplier);
        return () -> {
			try {
				return theSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link Predicate#test(Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception wrapped in a {@link RuntimeException}. This allows methods 
     * which previously could not be used as method references due to their 
     * checked exception to now be used.
     * 
     * @param <T> the type supplied to the predicate.
     * @param thePredicate the predicate.
     * 
     * @return the wrapped predicate. 
     * 
     * @throws NullPointerException if the predicate supplied is null.
    **/
    public static <T> Predicate<T> unchecked(
                                    PredicateWithException<T> thePredicate) {
        Objects.requireNonNull(thePredicate);
        return (theValue) -> {
            try {
                return thePredicate.test(theValue);
            }
            catch (RuntimeException re) {
                throw re;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    
    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link BiPredicate#test(Object,Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception wrapped in a {@link RuntimeException}. This allows methods 
     * which previously could not be used as method references due to their 
     * checked exception to now be used.
     * 
     * @param <T> the first type supplied to the predicate.
     * @param <U> the second type supplied to the predicate.
     * @param thePredicate the predicate.
     * 
     * @return the wrapped predicate. 
     * 
     * @throws NullPointerException if the predicate supplied is null.
    **/
    public static <T, U> BiPredicate<T, U> unchecked(
                                BiPredicateWithException<T, U> thePredicate) {
        Objects.requireNonNull(thePredicate);
        return (theFirstValue, theSecondValue) -> {
            try {
                return thePredicate.test(theFirstValue, theSecondValue);
            }
            catch (RuntimeException re) {
                throw re;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
	
    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link Consumer#accept(Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception using the function supplied. This allows methods which 
     * previously could not be used as method references due to their checked 
     * exception to now be used, and additionally allows control over the type 
     * of runtime exception thrown.
     * 
     * @param <T> the type to be consumed.
     * @param <E> the type of runtime exception to be thrown. 
     * @param theConsumer the consumer.
     * @param theExceptionFunc the function to be used to convert the exception
     *                         into a runtime exception.
     * 
     * @return the wrapped consumer. 
     * 
     * @throws NullPointerException if the consumer or exception function 
     *                              supplied is null.
    **/
	public static <T, E extends RuntimeException> Consumer<T> unchecked(
								ConsumerWithException<? super T> theConsumer,
								Function<Exception, E> theExceptionFunc) {
		Objects.requireNonNull(theConsumer);
        Objects.requireNonNull(theExceptionFunc);
        return (theValue) -> {
			try {
				theConsumer.accept(theValue);
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw theExceptionFunc.apply(e);
			}
		};
	}
	
    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link BiConsumer#accept(Object,Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception using the function supplied. This allows methods which 
     * previously could not be used as method references due to their checked 
     * exception to now be used, and additionally allows control over the type 
     * of runtime exception thrown.
     * 
     * @param <T> the first type to be consumed.
     * @param <U> the second type to be consumed.
     * @param <E> the type of runtime exception to be thrown. 
     * @param theConsumer the consumer.
     * @param theExceptionFunc the function to be used to convert the exception
     *                         into a runtime exception.
     * 
     * @return the wrapped consumer. 
     * 
     * @throws NullPointerException if the consumer or exception function 
     *                              supplied is null.
    **/
	public static <T, U, E extends RuntimeException> BiConsumer<T, U> unchecked(
					BiConsumerWithException<? super T, ? super U> theConsumer,
					Function<Exception, E> theExceptionFunc) {
		Objects.requireNonNull(theConsumer);
        Objects.requireNonNull(theExceptionFunc);
        return (theFirstValue, theSecondValue) -> {
			try {
				theConsumer.accept(theFirstValue, theSecondValue);
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw theExceptionFunc.apply(e);
			}
		};
	}

    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link Function#apply(Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception using the function supplied. This allows methods which 
     * previously could not be used as method references due to their checked 
     * exception to now be used, and additionally allows control over the type 
     * of runtime exception thrown.
     * 
     * @param <T> the type supplied to the function.
     * @param <R> the type returned by the function.
     * @param <E> the type of runtime exception to be thrown
     * @param theFunction the function.
     * @param theExceptionFunc the function to be used to convert the exception
     *                         into a runtime exception.
     * 
     * @return the wrapped function. 
     * 
     * @throws NullPointerException if the function or exception function 
     *                              supplied is null.
    **/
	public static <T, R, E extends RuntimeException> Function<T, R> unchecked(
								FunctionWithException<? super T, R> theFunction,
								Function<Exception, E> theExceptionFunc) {
		Objects.requireNonNull(theFunction);
        Objects.requireNonNull(theExceptionFunc);
        return (theValue) -> {
			try {
				return theFunction.apply(theValue);
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw theExceptionFunc.apply(e);
			}
		};
	}
    
    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link BiFunction#apply(Object,Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception using the function supplied. This allows methods which 
     * previously could not be used as method references due to their checked 
     * exception to now be used, and additionally allows control over the type 
     * of runtime exception thrown.
     * 
     * @param <T> the first type supplied to the function.
     * @param <U> the second type supplied to the function.
     * @param <R> the type returned by the function.
     * @param <E> the type of runtime exception to be thrown
     * @param theFunction the function.
     * @param theExceptionFunc the function to be used to convert the exception
     *                         into a runtime exception.
     * 
     * @return the wrapped function. 
     * 
     * @throws NullPointerException if the function or exception function 
     *                              supplied is null.
    **/
	public static <T, U, R, E extends RuntimeException> BiFunction<T, U, R> 
            unchecked(
                BiFunctionWithException<? super T, ? super U, R> theFunction,
				 Function<Exception, E> theExceptionFunc) {
		Objects.requireNonNull(theFunction);
        Objects.requireNonNull(theExceptionFunc);
        return (theFirstValue, theSecondValue) -> {
			try {
				return theFunction.apply(theFirstValue, theSecondValue);
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw theExceptionFunc.apply(e);
			}
		};
	}

            
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link Supplier#get()} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception using the function supplied. This allows methods which 
     * previously could not be used as method references due to their checked 
     * exception to now be used, and additionally allows control over the type 
     * of runtime exception thrown.
     * 
     * @param <T> the type returned by the supplier.
     * @param <E> the type of runtime exception to be thrown
     * @param theSupplier the supplier.
     * @param theExceptionFunc the function to be used to convert the exception
     *                         into a runtime exception.
     * 
     * @return the wrapped supplier. 
     * 
     * @throws NullPointerException if the supplier or exception function 
     *                              supplied is null.
    **/
	public static <T, E extends RuntimeException> Supplier<T> unchecked(
									SupplierWithException<T> theSupplier,
									Function<Exception, E> theExceptionFunc) {
		Objects.requireNonNull(theSupplier);
        Objects.requireNonNull(theExceptionFunc);
        return () -> {
			try {
				return theSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw theExceptionFunc.apply(e);
			}
		};
	}
    
    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link Predicate#test(Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception using the function supplied. This allows methods which 
     * previously could not be used as method references due to their checked 
     * exception to now be used, and additionally allows control over the type 
     * of runtime exception thrown.
     * 
     * @param <T> the type supplied to the predicate.
     * @param <E> the type of runtime exception to be thrown
     * @param thePredicate the predicate.
     * @param theExceptionFunc the function to be used to convert the exception
     *                         into a runtime exception.
     * 
     * @return the wrapped predicate. 
     * 
     * @throws NullPointerException if the predicate or exception function 
     *                              supplied is null.
    **/
    public static <T, E extends RuntimeException> Predicate<T> unchecked(
                                    PredicateWithException<T> thePredicate,
                                    Function<Exception, E> theExceptionFunc) {
        Objects.requireNonNull(thePredicate);
        Objects.requireNonNull(theExceptionFunc);
        return (theValue) -> {
            try {
                return thePredicate.test(theValue);
            }
            catch (RuntimeException re) {
                throw re;
            }
            catch (Exception e) {
                throw theExceptionFunc.apply(e);
            }
        };
    }
    
    
    /**
     * Method which wraps a functional interface whose functional method matches
     * the method signature of {@link BiPredicate#test(Object,Object)} but
     * additionally throws a checked exception, and re-throws the checked 
     * exception using the function supplied. This allows methods which 
     * previously could not be used as method references due to their checked 
     * exception to now be used, and additionally allows control over the type 
     * of runtime exception thrown.
     * 
     * @param <T> the first type supplied to the predicate.
     * @param <U> the second type supplied to the predicate.
     * @param <E> the type of runtime exception to be thrown
     * @param thePredicate the predicate.
     * @param theExceptionFunc the function to be used to convert the exception
     *                         into a runtime exception.
     * 
     * @return the wrapped predicate. 
     * 
     * @throws NullPointerException if the predicate or exception function 
     *                              supplied is null.
    **/
    public static <T, U, E extends RuntimeException> BiPredicate<T, U> 
            unchecked(
                    BiPredicateWithException<? super T, ? super U> thePredicate,
                    Function<Exception, E> theExceptionFunc) {
        Objects.requireNonNull(thePredicate);
        Objects.requireNonNull(theExceptionFunc);
        return (theFirstValue, theSecondValue) -> {
            try {
                return thePredicate.test(theFirstValue, theSecondValue);
            }
            catch (RuntimeException re) {
                throw re;
            }
            catch (Exception e) {
                throw theExceptionFunc.apply(e);
            }
        };
    }
}
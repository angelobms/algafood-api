package com.algaworks.algafood.api.exceptionhandler;

import static com.algaworks.algafood.api.exceptionhandler.ProblemType.ENTITY_IN_USE;
import static com.algaworks.algafood.api.exceptionhandler.ProblemType.ERRO_BUSINESS;
import static com.algaworks.algafood.api.exceptionhandler.ProblemType.INCOMPREHENSIBLE_MESSAGE;
import static com.algaworks.algafood.api.exceptionhandler.ProblemType.INVALID_DATA;
import static com.algaworks.algafood.api.exceptionhandler.ProblemType.INVALID_PARAMETER;
import static com.algaworks.algafood.api.exceptionhandler.ProblemType.RESOURCE_NOT_FOUND;
import static com.algaworks.algafood.api.exceptionhandler.ProblemType.SYSTEM_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String SYSTEM_ERRO_MSG = "An unexpected internal system error has occurred. "
			+ "Please try again and if the problem persists, contact your system administrator";
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	

	// 1. MethodArgumentTypeMismatchException is a subtype of TypeMismatchException

	// 2. ResponseEntityExceptionHandler already handles TypeMismatchException more
	// comprehensively

	// 3. So, we specialize the handleTypeMismatch method and check if the exception
	// is an instance of MethodArgumentTypeMismatchException

	// 4. If so, we call an expert method to handle this type of exception.

	// 5. We could do everything inside handleTypeMismatch, but I preferred to
	// separate it into another method
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = INVALID_PARAMETER;

		String detail = String.format(
				"URL parameter '%s' received value '%s',"
						+ "which is of an invalid type. Please enter a value compatible with type %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(SYSTEM_ERRO_MSG).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}

		ProblemType problemType = INCOMPREHENSIBLE_MESSAGE;
		String detail = "The request body is invalid. Check syntax error.";

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
	    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}  
	
	@ExceptionHandler({ ValidationExceptiom.class })
	public ResponseEntity<Object> handleValidacaoException(ValidationExceptiom ex, WebRequest request) {
	    return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), BAD_REQUEST, request);
	}  

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ProblemType problemType = INCOMPREHENSIBLE_MESSAGE;
		String detail = String.format("The property '%s' does not exists. "
				+ "Correct or remove this property and try again.", path);

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(SYSTEM_ERRO_MSG).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		ProblemType problemType = INCOMPREHENSIBLE_MESSAGE;
		String detail = String.format(
				"Property '%s' has been given invalid value '%s'. " + "Enter the value compatible with the type '%s'.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(SYSTEM_ERRO_MSG).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

		HttpStatus status = NOT_FOUND;
		ProblemType problemType = RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {

		HttpStatus status = CONFLICT;
		ProblemType problemType = ENTITY_IN_USE;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {

		HttpStatus status = BAD_REQUEST;
		ProblemType problemType = ERRO_BUSINESS;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = RESOURCE_NOT_FOUND;
		String detail = String.format("The resource '%s' does not exist.", ex.getRequestURL());

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(SYSTEM_ERRO_MSG).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
	    HttpStatus status = INTERNAL_SERVER_ERROR;		
	    ProblemType problemType = SYSTEM_ERROR;
	    String detail = SYSTEM_ERRO_MSG;

	    // It's important to put printStackTrace (at least for now, 
	    // we're not logging) to show stacktrace in the console. 
		// If you don't, you won't see the stacktrace of exceptions that 
		// would be important to you during, especially in the development phase.
	    ex.printStackTrace();
	    
	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.userMessage(detail).build();

	    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}         

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder()
					.timestamp(OffsetDateTime.now())
					.title(status.getReasonPhrase())
					.status(status.value())
					.userMessage(SYSTEM_ERRO_MSG)
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.timestamp(OffsetDateTime.now())
					.title((String) body)
					.status(status.value())
					.userMessage(SYSTEM_ERRO_MSG)
					.build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
				.timestamp(OffsetDateTime.now())
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = INVALID_DATA;
	    String detail = "One or more fields are invalid. Fill in correctly and try again.";
	    
	    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
	            .map(objectError -> {
	                String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
	                
	                String name = objectError.getObjectName();
	                
	                if (objectError instanceof FieldError) {
	                    name = ((FieldError) objectError).getField();
	                }
	                
	                return Problem.Object.builder()
	                    .name(name)
	                    .userMessage(message)
	                    .build();
	            })
	            .collect(Collectors.toList());
	    
	    Problem problem = createProblemBuilder(status, problemType, detail)
	        .userMessage(detail)
	        .objects(problemObjects)
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}          
}

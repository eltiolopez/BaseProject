package com.jld.base.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/errors")
public class GlobalDefaultHttpErrorController {
	
	private static final Logger logger = Logger.getLogger(GlobalDefaultHttpErrorController.class);
	
	public static final String DEFAULT_ERROR_VIEW = "errors/error_page";
	
	//  NoSuchRequestHandlingMethodException    404 (Not Found)
	//  HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
	//  HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
	//  MissingServletRequestParameterException 400 (Bad Request)
	//  ServletRequestBindingException          400 (Bad Request)
	//  ConversionNotSupportedException         500 (Internal Server Error)
	//  TypeMismatchException                   400 (Bad Request)
	//  HttpMessageNotReadableException         400 (Bad Request)
	//  HttpMessageNotWritableException         500 (Internal Server Error)
	//  MethodArgumentNotValidException         400 (Bad Request)
	//  MissingServletRequestPartException      400 (Bad Request)

	@RequestMapping(value = "/errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
         
        ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
 
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Internal Server Error";
                break;
            }
        }

        //mav.addObject("datetime", new Date());
        mav.addObject("numHttpError", httpErrorCode);
        mav.addObject("errorMsg", errorMsg);
        //mav.addObject("url", httpRequest.getRequestURL());
        return mav;
    }
    
    @RequestMapping(value = "/uncaughtException", method = RequestMethod.GET)
    public ModelAndView renderExceptionPage(HttpServletRequest httpRequest) {
         
        ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
        String httpErrorCode = "Unknown error";
        String errorMsg = "Internal exception of type ";

        Throwable throwable = getThrowable(httpRequest);
        String exceptionName = throwable.getClass().getName();
        
        String starkTrace = ExceptionUtils.getStackTrace(throwable);
        logger.error("Unknown exception: " + starkTrace);

        //mav.addObject("datetime", new Date());
        mav.addObject("numHttpError", httpErrorCode);
        mav.addObject("errorMsg", errorMsg + exceptionName);
        mav.addObject("starkTrace", starkTrace);
        
        return mav;
    }
    
   private int getErrorCode(HttpServletRequest httpRequest) {
	   if(httpRequest.getAttribute("javax.servlet.error.status_code") != null) {
		   return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	   }
	   else {
		   return -1;
	   }
   }
   
  private Throwable getThrowable(HttpServletRequest httpRequest) {
      return (Throwable) httpRequest.getAttribute("javax.servlet.error.exception");
  }
}

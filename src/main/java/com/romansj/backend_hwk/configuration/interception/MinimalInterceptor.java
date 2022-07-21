package com.romansj.backend_hwk.configuration.interception;

import com.romansj.backend_hwk.utils.HttpServletUtils;
import com.romansj.backend_hwk.utils.models.ObservableSubject;
import io.reactivex.rxjava3.core.Observable;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sun.activation.registries.LogSupport.log;


// Found it easier instead to set an Observable in Controller (for payment requests), but kept this class for debugging purposes
@Component
@Slf4j
public class MinimalInterceptor implements HandlerInterceptor {

    private ObservableSubject<InterceptionData> observable;

    public Observable<InterceptionData> getObservable() {
        return observable.getObservable();
    }

    public MinimalInterceptor() {
        observable = new ObservableSubject<>();
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        log.debug("MINIMAL: INTERCEPTOR PREHANDLE CALLED");

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("MINIMAL: INTERCEPTOR POSTHANDLE CALLED");

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        log.debug("MINIMAL: INTERCEPTOR AFTERCOMPLETION CALLED");

        log.debug(HttpServletUtils.getHeaderData(request));
        log.debug( HttpServletUtils.getHeaderData(response));


        observable.setValue(new InterceptionData(request, response, ex));


        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


}

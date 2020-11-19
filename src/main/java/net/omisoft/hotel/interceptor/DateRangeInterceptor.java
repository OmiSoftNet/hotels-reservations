package net.omisoft.hotel.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.omisoft.hotel.configuration.MessageSourceConfiguration;
import net.omisoft.hotel.pojo.CustomMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
@AllArgsConstructor
public class DateRangeInterceptor implements HandlerInterceptor {

    public static final String START_DATE_PARAM = "from";
    public static final String END_DATE_PARAM = "to";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    private final MessageSourceConfiguration message;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("GET")) {
            try {
                String from = request.getParameter(START_DATE_PARAM);
                String to = request.getParameter(END_DATE_PARAM);

                if (from == null && to == null) {
                    return true;
                }

                LocalDate fromDate = LocalDate.parse(String.valueOf(from));
                LocalDate toDate = LocalDate.parse(String.valueOf(to));
                if (!fromDate.isBefore(toDate)) {
                    initBadResponse(response);
                    return false;
                }
            } catch (DateTimeParseException ex) {
                initBadResponse(response);
                return false;
            }
        }
        return true;
    }

    private void initBadResponse(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        String msg = message.getMessage("exception.date_range.interceptor", new Object[]{START_DATE_PARAM, END_DATE_PARAM, DATE_FORMAT_PATTERN});
        response.getOutputStream().write(objectMapper.writeValueAsString(new CustomMessage(msg)).getBytes());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

}

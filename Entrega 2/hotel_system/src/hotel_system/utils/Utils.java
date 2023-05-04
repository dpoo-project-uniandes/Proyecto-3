package hotel_system.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Utils {
	
	public static final Date nowDate() {
		return Date.valueOf(LocalDateTime.now().toLocalDate());
	}
	
	public static final String stringDate(Date date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return dtf.format(date.toLocalDate());
	}
	public static Date stringToDate(String fecha) {
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	    LocalDate localDate = LocalDate.parse(fecha, dtf);
	    return Date.valueOf(localDate);
	}
	
	public static final String stringLocalDate(Date date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return dtf.format(date.toLocalDate());
	}
	
	public static final Integer sustractDates(Date date1, Date date2) {
		Long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
		return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
	
	public static final Long generateId() {
		return nowDate().getTime();
	}
}

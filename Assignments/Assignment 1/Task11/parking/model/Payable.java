package parking.model;
import java.time.LocalDate;
public interface Payable { boolean isFeeExempt(LocalDate day); }

package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.util.Date;

public class Reservation {
    private Long reservationId;
    private Long accommodationId;
    private Long guestId;
    private Date checkInDate;
    private Date checkOutDate;


    public Reservation(Long reservationId, Long accommodationId, Long guestId, Date checkInDate, Date checkOutDate) {
        this.reservationId = reservationId;
        this.accommodationId = accommodationId;
        this.guestId = guestId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}

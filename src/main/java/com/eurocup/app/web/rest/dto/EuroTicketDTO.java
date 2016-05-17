package com.eurocup.app.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the EuroTicket entity.
 */
public class EuroTicketDTO implements Serializable {

    private Long id;

    @NotNull
    private String match;


    @NotNull
    private String location;


    @NotNull
    private String phase;


    @NotNull
    private String matchgroup;


    @NotNull
    private LocalDate matchDate;


    private String matchHour;


    @NotNull
    private BigDecimal price;


    private String image;


    @NotNull
    private Long totalTickets;


    @NotNull
    private Long nrOfTickets;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
    public String getMatchgroup() {
        return matchgroup;
    }

    public void setMatchgroup(String matchgroup) {
        this.matchgroup = matchgroup;
    }
    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }
    public String getMatchHour() {
        return matchHour;
    }

    public void setMatchHour(String matchHour) {
        this.matchHour = matchHour;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Long getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Long totalTickets) {
        this.totalTickets = totalTickets;
    }
    public Long getNrOfTickets() {
        return nrOfTickets;
    }

    public void setNrOfTickets(Long nrOfTickets) {
        this.nrOfTickets = nrOfTickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EuroTicketDTO euroTicketDTO = (EuroTicketDTO) o;

        if ( ! Objects.equals(id, euroTicketDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EuroTicketDTO{" +
            "id=" + id +
            ", match='" + match + "'" +
            ", location='" + location + "'" +
            ", phase='" + phase + "'" +
            ", matchgroup='" + matchgroup + "'" +
            ", matchDate='" + matchDate + "'" +
            ", matchHour='" + matchHour + "'" +
            ", price='" + price + "'" +
            ", image='" + image + "'" +
            ", totalTickets='" + totalTickets + "'" +
            ", nrOfTickets='" + nrOfTickets + "'" +
            '}';
    }
}

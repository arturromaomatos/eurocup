package com.eurocup.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EuroTicket.
 */
@Entity
@Table(name = "euro_ticket")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EuroTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "match", nullable = false)
    private String match;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "phase", nullable = false)
    private String phase;

    @NotNull
    @Column(name = "matchgroup", nullable = false)
    private String matchgroup;

    @NotNull
    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    @Column(name = "match_hour")
    private String matchHour;

    @NotNull
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "total_tickets", nullable = false)
    private Long totalTickets;

    @NotNull
    @Column(name = "nr_of_tickets", nullable = false)
    private Long nrOfTickets;

    @OneToOne(mappedBy = "ticket")
    @JsonIgnore
    private EuroOrderItem name;

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

    public EuroOrderItem getName() {
        return name;
    }

    public void setName(EuroOrderItem euroOrderItem) {
        this.name = euroOrderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EuroTicket euroTicket = (EuroTicket) o;
        if(euroTicket.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, euroTicket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EuroTicket{" +
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

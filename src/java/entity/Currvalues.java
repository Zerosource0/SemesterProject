/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adam
 */
@Entity
@Table(name = "currvalues")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Currvalues.findAll", query = "SELECT c FROM Currvalues c"),
    @NamedQuery(name = "Currvalues.findByCurr", query = "SELECT c FROM Currvalues c WHERE c.curr = :curr"),
    @NamedQuery(name = "Currvalues.findById", query = "SELECT c FROM Currvalues c WHERE c.id = :id"),
    @NamedQuery(name="Currvalues.findDesc", query = "SELECT cd.description FROM Currdesc cd, Currvalues cv WHERE cd.code=:code")})
public class Currvalues implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "date")
    private String date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "curr")
    private double curr;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "code", referencedColumnName = "code")
    @ManyToOne(optional = false)
    private Currdesc code;

    public Currvalues() {
    }

    public Currvalues( String date, double curr, Currdesc code) {
       
        this.date = date;
        this.curr = curr;
        this.code=code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCurr() {
        return curr;
    }

    public void setCurr(double curr) {
        this.curr = curr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currdesc getCode() {
        return code;
    }

    public void setCode(Currdesc code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currvalues)) {
            return false;
        }
        Currvalues other = (Currvalues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Currvalues{" + "date=" + date + ", curr=" + curr + ", code=" + code + '}';
    }

    
    
}

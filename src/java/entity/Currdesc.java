/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adam
 */
@Entity
@Table(name = "currdesc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Currdesc.findAll", query = "SELECT c FROM Currdesc c"),
    @NamedQuery(name = "Currdesc.findByCode", query = "SELECT c FROM Currdesc c WHERE c.code = :code")})
public class Currdesc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "code")
    private String code;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "code")
    private Collection<Currvalues> currvaluesCollection = new ArrayList<>();

    public Currdesc() {
    }

    public void addCurrValue (Currvalues cv)
    {
        currvaluesCollection.add(cv);
    }
    
    public Currdesc(String code) {
        this.code = code;
    }
    public Currdesc(String code, String description) 
    {
        this.description=description;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Currvalues> getCurrvaluesCollection() {
        return currvaluesCollection;
    }

    public void setCurrvaluesCollection(Collection<Currvalues> currvaluesCollection) {
        this.currvaluesCollection = currvaluesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currdesc)) {
            return false;
        }
        Currdesc other = (Currdesc) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Currdesc[ code=" + code + " ] "+ "description:" +description;
    }
    
}

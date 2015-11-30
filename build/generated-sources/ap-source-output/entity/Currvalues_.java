package entity;

import entity.Currdesc;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-29T19:13:38")
@StaticMetamodel(Currvalues.class)
public class Currvalues_ { 

    public static volatile SingularAttribute<Currvalues, Integer> id;
    public static volatile SingularAttribute<Currvalues, Double> curr;
    public static volatile SingularAttribute<Currvalues, Currdesc> code;
    public static volatile SingularAttribute<Currvalues, String> date;

}
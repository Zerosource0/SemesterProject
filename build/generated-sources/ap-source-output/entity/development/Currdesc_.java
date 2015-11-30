package entity.development;

import entity.Currdesc;
import entity.Currvalues;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-29T19:13:38")
@StaticMetamodel(Currdesc.class)
public class Currdesc_ { 

    public static volatile CollectionAttribute<Currdesc, Currvalues> currvaluesCollection;
    public static volatile SingularAttribute<Currdesc, String> description;
    public static volatile SingularAttribute<Currdesc, String> code;

}
package fr.upem.spacekaira.shape.characters.enemies;

import com.sun.xml.internal.txw2.annotation.XmlElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum EnemyType {
//    @XmlEnumValue(value="TIE")      TIE,
//    @XmlEnumValue(value="SQUADRON") SQUADRON,
//    @XmlEnumValue(value="CRUISER")  CRUISER,
//    @XmlEnumValue(value="BRUTE")    BRUTE,
//    @XmlEnumValue(value="ROTATINGTRIANGLE") ROTATINGTRIANGLE;

//   XmlAttribute @XmlAttribute(name="type") TIE,
//    @XmlAttribute(name="type") SQUADRON,
//    @XmlAttribute(name="type") CRUISER,
//    @XmlAttribute(name="type") BRUTE,
//    @XmlAttribute(name="type") ROTATINGTRIANGLE;

    TIE,
    SQUADRON,
    CRUISER,
    BRUTE,
    ROTATINGTRIANGLE;
}

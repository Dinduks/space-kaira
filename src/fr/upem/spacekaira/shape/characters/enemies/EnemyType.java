package fr.upem.spacekaira.shape.characters.enemies;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
public enum EnemyType {
    @XmlEnumValue(value="TIE")      TIE,
    @XmlEnumValue(value="SQUADRON") SQUADRON,
    @XmlEnumValue(value="CRUISER")  CRUISER,
    @XmlEnumValue(value="BRUTE")    BRUTE,
    @XmlEnumValue(value="ROTATINGTRIANGLE") ROTATINGTRIANGLE;
}

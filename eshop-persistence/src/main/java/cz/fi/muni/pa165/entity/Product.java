package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Color;

import javax.persistence.*;
import java.util.Date;


@Table(name = "ESHOP_PRODUCTS")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Enumerated
    Color color;

    @Temporal(value = TemporalType.DATE)
    Date addedDate;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (!getName().equals(product.getName())) return false;
        if (getColor() != product.getColor()) return false;
        return getAddedDate() != null ? getAddedDate().equals(product.getAddedDate()) : product.getAddedDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        result = 31 * result + (getAddedDate() != null ? getAddedDate().hashCode() : 0);
        return result;
    }
}

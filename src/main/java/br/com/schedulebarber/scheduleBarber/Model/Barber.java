package br.com.schedulebarber.scheduleBarber.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Sex sex;
    private String phone;
    private String address;
    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "access_id", referencedColumnName = "id")
    private Access access;

    public Barber(Long id, String name, Sex sex, String phone, String address, LocalDate birthday, Access access) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.access = access;
        this.birthday = birthday;
    }

    public Barber() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}

package com.felipebatista.multitenancia.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vw_clientes")
@SequenceGenerator(name = "clientes_seq", sequenceName = "clientes_seq", allocationSize = 1)
public class Cliente extends AbstractEntityImpl {

    @Id
    @Column(name = "i_cliente")
    @GeneratedValue(generator = "clientes_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                "} " + super.toString();
    }
}

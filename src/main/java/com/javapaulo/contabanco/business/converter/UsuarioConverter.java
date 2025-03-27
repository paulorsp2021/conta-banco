package com.javapaulo.contabanco.business.converter;

import com.javapaulo.contabanco.business.dto.ContaDTO;
import com.javapaulo.contabanco.business.dto.EnderecoDTO;
import com.javapaulo.contabanco.business.dto.TelefoneDTO;
import com.javapaulo.contabanco.business.dto.UsuarioDTO;
import com.javapaulo.contabanco.entity.Conta;
import com.javapaulo.contabanco.entity.Endereco;
import com.javapaulo.contabanco.entity.Telefone;
import com.javapaulo.contabanco.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ConcurrentModificationException;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
       return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(usuarioDTO.getEnderecos() != null ?
                    paraListaEndereco(usuarioDTO.getEnderecos()) : null)
                .telefones(usuarioDTO.getTelefones() != null ?
                    paraListaTelefones(usuarioDTO.getTelefones()) : null)
                .contas(usuarioDTO.getContas() != null ?
                    paraListaContas(usuarioDTO.getContas()) : null)
                .build();
    }

    private List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecosDTOS) {
        return enderecosDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(usuario.getEnderecos() != null ?
                        paraListaEnderecoDTO(usuario.getEnderecos()) : null)
                .telefones(usuario.getTelefones() != null ?
                        paraListaTelefonesDTO(usuario.getTelefones()) : null)
                .contas(usuario.getContas() != null ?
                        paraListaContasDTO(usuario.getContas()) : null)
                .build();
    }

    private List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefones){
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefones){
        return TelefoneDTO.builder()
                .id(telefones.getId())
                .numero(telefones.getNumero())
                .ddd(telefones.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha()!= null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail()!=null?usuarioDTO.getEmail():entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .contas(entity.getContas())
                .build();
    }

    public  Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() !=null?dto.getRua(): entity.getRua())
                .numero(dto.getNumero()!=null? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade()!=null? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep()!=null? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento()!=null? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado()!=null? dto.getEstado() : entity.getEstado())
                .build();
    }
    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero()!=null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd()!= null? dto.getDdd() : entity.getDdd())
                .build();
    }
    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario){
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario){
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuario_id(idUsuario)
                .build();
    }

    public List<Conta> paraListaContas(List<ContaDTO> contaDTOS){
        return contaDTOS.stream().map(this::paraConta).toList();
    }

    public Conta paraConta(ContaDTO contaDTO){
        return Conta.builder()
                .id(contaDTO.getId())
                .agencia(contaDTO.getAgencia())
                .conta(contaDTO.getConta())
                .saldo(contaDTO.getSaldo())
                .build();
    }

    private List<ContaDTO> paraListaContasDTO(List<Conta> contas) {
        return contas.stream().map(this::paraContasDTO).toList();
    }

    public ContaDTO paraContasDTO(Conta conta){
        return ContaDTO.builder()
                .id(conta.getId())
                .agencia(conta.getAgencia())
                .conta(conta.getConta())
                .saldo(conta.getSaldo())
                .build();
    }
}

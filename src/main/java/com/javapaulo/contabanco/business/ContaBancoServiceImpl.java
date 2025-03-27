package com.javapaulo.contabanco.business;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import com.javapaulo.contabanco.business.converter.UsuarioConverter;
import com.javapaulo.contabanco.business.dto.ContaDTO;
import com.javapaulo.contabanco.business.dto.UsuarioDTO;
import com.javapaulo.contabanco.entity.Conta;
import com.javapaulo.contabanco.entity.Usuario;
import com.javapaulo.contabanco.exceptions.ConflictException;
import com.javapaulo.contabanco.exceptions.ResourceNotFoundException;
import com.javapaulo.contabanco.repository.ContaRepository;
import com.javapaulo.contabanco.repository.EnderecoRepository;
import com.javapaulo.contabanco.repository.TelefoneRepository;
import com.javapaulo.contabanco.repository.UsuarioRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContaBancoServiceImpl implements ContaBancoService{

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final ContaRepository contaRepository;

    
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste((usuarioDTO.getEmail()));
        usuarioDTO.setSenha(usuarioDTO.getSenha());
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {

        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Email não encontrado " + email)
                            )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email){

        usuarioRepository.deleteByEmail(email);
    }

    @Override
    public List<Conta> getAllContas() {
        return contaRepository.findAll();
    }

    @Override
    public Conta getContaById(Long id) {
        Optional<Conta> optional = contaRepository.findById(id);
        Conta conta = null;
        if (optional.isPresent()) {
            conta = optional.get();
        } else {
            throw new RuntimeException(" Conta não localizado pelo id :: " + id);
        }
        return conta;
    }

    public ContaDTO salvaConta(ContaDTO contaDTO) {
        Conta conta = usuarioConverter.paraConta(contaDTO);
        return usuarioConverter.paraContasDTO(contaRepository.save(conta));

    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public String deposita(ContaDTO contaDTO){

        Conta conta = usuarioConverter.paraConta(contaDTO);

        conta.setSaldo(conta.getSaldo()+contaDTO.getValor());
        contaRepository.save(conta);

        return "Deposito Efetuado com Sucesso";

    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public String sacar(ContaDTO contaDTO){
        Conta conta = usuarioConverter.paraConta(contaDTO);

        if (conta.getSaldo() - contaDTO.getValor()>0) {
            conta.setSaldo(conta.getSaldo() - contaDTO.getValor());
            contaRepository.save(conta);

            return "Saque Efetuado com Sucesso";
        } else {
            throw new RuntimeException("Conta não pode ser negativa");
        }
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public String tranferir(ContaDTO contaDTO) {

        try {
            Conta contadest = findByAgenciaAndConta(contaDTO.getAgenciaDest(),contaDTO.getContaDest());
            Conta contaOrig = usuarioConverter.paraConta(contaDTO);

            if (contaOrig.getSaldo() - contaDTO.getValor()>0) {

                contaOrig.setSaldo(contaOrig.getSaldo() - contaDTO.getValor());
                contaRepository.save(contaOrig);

                contadest.setSaldo(contadest.getSaldo() + contaDTO.getValor());
                contaRepository.save(contadest);
                return "Transferencia Efetuado com Sucesso";
            } else {
                throw new RuntimeException("Conta não pode ser negativa");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Conta não encontrada!", e.getCause());
        }

    }

    @Override
    public Conta findByAgenciaAndConta(String agencia, String contaD) {
        Optional<Conta> optional = contaRepository.findByAgenciaAndConta(agencia,contaD);
        Conta conta = null;
        if (optional.isPresent()) {
            conta = optional.get();
        } else {
            throw new RuntimeException(" Conta não localizado pelo Agencia,Conta :: " + agencia +" "+ contaD);
        }
        return conta;
    }
}

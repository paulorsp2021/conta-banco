package com.javapaulo.contabanco.controller;

import com.javapaulo.contabanco.business.ContaBancoServiceImpl;
import com.javapaulo.contabanco.business.converter.UsuarioConverter;
import com.javapaulo.contabanco.business.dto.ContaDTO;
import com.javapaulo.contabanco.business.dto.UsuarioDTO;
import com.javapaulo.contabanco.entity.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class ContaBancoController {

    private final ContaBancoServiceImpl contaBancoServiceImpl;
    private final UsuarioConverter usuarioConverter;

    @GetMapping("/")
    public String paginaInicial(Model model) {
        List<Conta> listContas = contaBancoServiceImpl.getAllContas();
        model.addAttribute("listContas", listContas);
        return "index";
    }

    @GetMapping("/cadastraContaForm")
    public String cadastraContaForm(Model model) {

        ContaDTO contaDTO = new ContaDTO();
        model.addAttribute("conta", contaDTO);
        return "cadastra_conta";
    }

    @PostMapping("/salvaConta")
    public String salvaConta(@ModelAttribute("conta") ContaDTO contaDTO) {
        contaBancoServiceImpl.salvaConta(contaDTO);
        return "redirect:/";
    }

    @PostMapping("/salvaUsuario")
    public String salvaUsuario(@ModelAttribute("usuario") UsuarioDTO usuarioDTO) {
        contaBancoServiceImpl.salvaUsuario(usuarioDTO);
        return "redirect:/";
    }

    @GetMapping("/depositarForm/{id}")
    public String depositarForm(@PathVariable ( value = "id") Long id, Model model) {

        Conta conta = contaBancoServiceImpl.getContaById(id);
        ContaDTO contaDTO = usuarioConverter.paraContasDTO(conta);
        model.addAttribute("conta", contaDTO);
        return "deposito_conta";
    }

    @PostMapping("/deposito")
    public String deposito(@ModelAttribute("conta") ContaDTO contaDTO) {

        contaBancoServiceImpl.deposita(contaDTO);
        return "redirect:/";
    }

    @GetMapping("/retirarForm/{id}")
    public String retirarForm(@PathVariable ( value = "id") Long id, Model model) {

        Conta conta = contaBancoServiceImpl.getContaById(id);
        ContaDTO contaDTO = usuarioConverter.paraContasDTO(conta);
        model.addAttribute("conta", contaDTO);
        return "retirada_conta";
    }

    @PostMapping("/sacar")
    public String sacar(@ModelAttribute("conta") ContaDTO contaDTO) {

        contaBancoServiceImpl.sacar(contaDTO);
        return "redirect:/";
    }

    @GetMapping("/transferirForm/{id}")
    public String transferirForm(@PathVariable ( value = "id") Long id, Model model) {

        Conta conta = contaBancoServiceImpl.getContaById(id);
        ContaDTO contaDTO = usuarioConverter.paraContasDTO(conta);
        model.addAttribute("conta", contaDTO);
        return "transferencia_conta";
    }

    @PostMapping("/transferir")
    public String transferir(@ModelAttribute("conta") ContaDTO contaDTO) {

        contaBancoServiceImpl.tranferir(contaDTO);
        return "redirect:/";
    }


}

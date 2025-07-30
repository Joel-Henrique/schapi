package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.*;
import br.ufjf.schapi.model.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final HospedagemRepository hospedagemRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final TipoServicoRepository tipoServicoRepository;

    public ServicoService(ServicoRepository servicoRepository,
                          HospedagemRepository hospedagemRepository,
                          FuncionarioRepository funcionarioRepository,
                          TipoServicoRepository tipoServicoRepository) {
        this.servicoRepository = servicoRepository;
        this.hospedagemRepository = hospedagemRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.tipoServicoRepository = tipoServicoRepository;
    }

    public List<Servico> getServicos() {
        return servicoRepository.findAll();
    }

    public Optional<Servico> getServicoById(Long id) {
        return servicoRepository.findById(id);
    }

    @Transactional
    public Servico salvar(Servico servico, Long hospedagemId, Long funcionarioId, Long tipoServicoId) {
        Hospedagem hospedagem = hospedagemRepository.findById(hospedagemId)
                .orElseThrow(() -> new RegraNegocioException("Hospedagem não encontrada"));

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RegraNegocioException("Funcionário não encontrado"));

        TipoServico tipoServico = tipoServicoRepository.findById(tipoServicoId)
                .orElseThrow(() -> new RegraNegocioException("Tipo de serviço não encontrado"));

        servico.setHospedagem(hospedagem);
        servico.setFuncionario(funcionario);
        servico.setTipoServico(tipoServico);

        validar(servico);
        return servicoRepository.save(servico);
    }

    @Transactional
    public void excluir(Servico servico) {
        Objects.requireNonNull(servico.getId());
        servicoRepository.delete(servico);
    }

    public void validar(Servico servico) {
        if (servico.getTipo() == null || servico.getTipo().trim().isEmpty()) {
            throw new RegraNegocioException("Tipo é obrigatório");
        }

        if (servico.getDescricao() == null || servico.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição é obrigatória");
        }

        if (servico.getDataServico() == null) {
            throw new RegraNegocioException("Data do serviço é obrigatória");
        }
    }
}

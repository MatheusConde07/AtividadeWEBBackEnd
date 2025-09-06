package br.com.uninter.gerenciadortarefas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tarefas")

public class TarefaController {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	// Endpoint para CRIAR uma nova tarefa (POST)
	@PostMapping
	public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
		return tarefaRepository.save(tarefa);
	}
	
	// Endpoint para LISTAR TODAS as tarefas (GET)
	
	@GetMapping 
		public List<Tarefa> listarTodasAsTarefas() {
			return tarefaRepository.findAll();
	}
	
	// Endpoint para buscar uma tarefa pelo o ID (GET)
	@GetMapping("/{id}")
		public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id){
			Optional<Tarefa> tarefa = tarefaRepository.findById(id);
			if (tarefa.isPresent()) {
				return ResponseEntity.ok(tarefa.get());
			}
			return ResponseEntity.notFound().build();
		}
	// Endpoint para atualizar uma tarefa (PUT)
	@PutMapping("/{id}")
	public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa detalhesTarefa){
		Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
		
		if (!tarefaOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Tarefa tarefaExistente = tarefaOptional.get();
		tarefaExistente.setNome(detalhesTarefa.getNome());
		tarefaExistente.setDataEntrega(detalhesTarefa.getDataEntrega());
		tarefaExistente.setResponsavel(detalhesTarefa.getResponsavel());
		
		final Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);
		return ResponseEntity.ok(tarefaAtualizada);
	}
	// Endpoint para deletar uma tarefa (DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarTarefa(@PathVariable Long id){
		if(!tarefaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		tarefaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}

package com.swift.backend.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swift.backend.model.Usuario;
import com.swift.backend.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
=======
import com.swift.backend.model.ErrorResponse;
import com.swift.backend.model.Usuario;
import com.swift.backend.service.UsuarioService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    @Inject
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Get
    public HttpResponse<?> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            return HttpResponse.ok(usuarios);
        } catch (SQLException e) {
            log.error("Erro ao buscar usuários no banco de dados", e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar usuários",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar usuários", e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Get("/{id}")
    public HttpResponse<?> getUsuarioById(@PathVariable Integer id) {
>>>>>>> 64a77131b5ede24b0fc69f28b922c7d231c94237
        try {
            Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
            
            if (usuario.isPresent()) {
<<<<<<< HEAD
                return ResponseEntity.ok(usuario.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario created = usuarioService.createUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            usuarioService.updateUsuario(id, usuario);
            return ResponseEntity.ok("Usuário atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
=======
                return HttpResponse.ok(usuario.get());
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Usuário não encontrado", "NOT_FOUND"));
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar usuário com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao buscar usuário",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar usuário com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Post
    public HttpResponse<?> createUsuario(@Body Usuario usuario) {
        try {
            Usuario created = usuarioService.createUsuario(usuario);
            return HttpResponse.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao criar usuário: {}", e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getMessage(), "VALIDATION_ERROR");
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (SQLException e) {
            log.error("Erro ao criar usuário no banco de dados", e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao criar usuário",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar usuário", e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Put("/{id}")
    public HttpResponse<?> updateUsuario(@PathVariable Integer id, @Body Usuario usuario) {
        try {
            usuarioService.updateUsuario(id, usuario);
            return HttpResponse.ok(new ErrorResponse("Usuário atualizado com sucesso", "SUCCESS"));
        } catch (IllegalArgumentException e) {
            log.warn("Erro de validação ao atualizar usuário com id {}: {}", id, e.getMessage());
            ErrorResponse error = new ErrorResponse(e.getMessage(), "VALIDATION_ERROR");
            return HttpResponse.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (SQLException e) {
            log.error("Erro ao atualizar usuário com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao atualizar usuário",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar usuário com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteUsuario(@PathVariable Integer id) {
>>>>>>> 64a77131b5ede24b0fc69f28b922c7d231c94237
        try {
            boolean deleted = usuarioService.deleteUsuario(id);
            
            if (deleted) {
<<<<<<< HEAD
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
=======
                return HttpResponse.noContent();
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Usuário não encontrado", "NOT_FOUND"));
            }
        } catch (SQLException e) {
            log.error("Erro ao deletar usuário com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro ao deletar usuário",
                "DATABASE_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar usuário com id: " + id, e);
            ErrorResponse error = new ErrorResponse(
                "Erro interno no servidor",
                "INTERNAL_ERROR",
                e.getMessage()
            );
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
>>>>>>> 64a77131b5ede24b0fc69f28b922c7d231c94237
        }
    }
}


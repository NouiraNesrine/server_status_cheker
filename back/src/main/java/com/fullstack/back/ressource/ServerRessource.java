package com.fullstack.back.ressource;


import com.fullstack.back.enumeration.Status;
import com.fullstack.back.model.Response;
import com.fullstack.back.model.Server;
import com.fullstack.back.service.ServerService;
import org.apache.tomcat.jni.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/server")
public class ServerRessource {

    private final ServerService serverService;

    public ServerRessource(ServerService serverService){
       this.serverService = serverService;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("servers",serverService.list(10)))
                        .statusCode(OK.value())
                        .httpStatus(OK)
                        .devMessage("servers retrieved")
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server",server))
                        .statusCode(OK.value())
                        .httpStatus(OK)
                        .devMessage(server.getStatus() == Status.UP ?"ping success":"ping failed")
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server",serverService.create(server)))
                        .statusCode(CREATED.value())
                        .httpStatus(CREATED)
                        .devMessage("server created")
                        .build()
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Response> updateServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server",serverService.update(server)))
                        .statusCode(CREATED.value())
                        .httpStatus(CREATED)
                        .devMessage("server updated")
                        .build()
        );
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server",serverService.get(id)))
                        .statusCode(CREATED.value())
                        .httpStatus(CREATED)
                        .devMessage("server retrieved")
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("deleted",serverService.delete(id)))
                        .statusCode(CREATED.value())
                        .httpStatus(CREATED)
                        .devMessage("server deleted")
                        .build()
        );
    }

    @GetMapping(path="/img/{fileName}",produces = IMAGE_JPEG_VALUE)
    public byte[] getServerImage(@PathVariable String fileName) throws IOException {
        return Files.readAllBytes(Paths.get("D:\\workspace\\intellij\\workshop\\img\\"+fileName));
    }
}

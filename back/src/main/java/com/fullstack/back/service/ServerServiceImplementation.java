package com.fullstack.back.service;

import com.fullstack.back.model.Server;
import com.fullstack.back.repository.ServerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

import static com.fullstack.back.enumeration.Status.DOWN;
import static com.fullstack.back.enumeration.Status.UP;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
@Slf4j
public class ServerServiceImplementation  implements ServerService{

    private final ServerRepo serverRepo;

    public ServerServiceImplementation(ServerRepo serverRepo){
        this.serverRepo = serverRepo;
    }
    @Override
    public Server create(Server server) {
        log.info("Saving new server:{}",server.getName());
        server.setImageURL(setServerImageURL());
        return serverRepo.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("fetching all servers");
        return serverRepo.findAll(of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("fetching server by id:{}",id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("updating server :{}",server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("deleting server by id:{}",id);
        serverRepo.deleteById(id);
        return true;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("pinging server ip:{}",ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? UP: DOWN);
        serverRepo.save(server);
        return server;
    }

    private String setServerImageURL() {
        String[] imgs = {"server1.jpg","server2.jpg","server3.jpg","server4.jpg"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/img"+imgs[new Random().nextInt(4)]).toUriString();
    }

}

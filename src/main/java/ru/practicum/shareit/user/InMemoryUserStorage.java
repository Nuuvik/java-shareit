package ru.practicum.shareit.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.EmailAlreadyExistsException;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Data
@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    HashMap<Long, User> userRepository = new HashMap<>();

    HashSet<String> emails = new HashSet<>();
    long generatorId = 0;

    public long generateId() {
        return ++generatorId;
    }

    @Override
    public void save(User user) {
        if (emails.contains(user.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        user.setId(generateId());
        userRepository.put(user.getId(), user);
        emails.add(user.getEmail());
    }

    @Override
    public void update(Long id, User user) {

        if (!userRepository.containsKey(id)) {
            throw new NotFoundException();
        }
        if (user.getEmail() != null) {
            if ((emails.contains(user.getEmail())) && !(userRepository.get(id).getEmail().equals(user.getEmail()))) {
                throw new EmailAlreadyExistsException();
            }
            emails.remove(userRepository.get(id).getEmail());
            userRepository.get(id).setEmail(user.getEmail());
            emails.add(user.getEmail());
        }
        if (user.getName() != null) {
            userRepository.get(id).setName(user.getName());
        }
    }

    @Override
    public void delete(Long id) {

        if (!userRepository.containsKey(id)) {
            throw new NotFoundException();
        }
        emails.remove(userRepository.get(id).getEmail());
        userRepository.remove(id);
    }


    @Override
    public User getUser(Long id) {
        if (!userRepository.containsKey(id)) {
            throw new NotFoundException();
        }
        return userRepository.get(id);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userRepository.values());
    }

    @Override
    public List<Long> getUsersId() {
        return new ArrayList<>(userRepository.keySet());
    }
}

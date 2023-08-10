package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.EmailAlreadyExistsException;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.*;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> userRepository = new HashMap<>();

    private Set<String> emails = new HashSet<>();
    private long generatorId = 0;

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
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            if ((emails.contains(user.getEmail())) && !(userRepository.get(id).getEmail().equals(user.getEmail()))) {
                throw new EmailAlreadyExistsException();
            }
            emails.remove(userRepository.get(id).getEmail());
            userRepository.get(id).setEmail(user.getEmail());
            emails.add(user.getEmail());
        }
        if (user.getName() != null && !user.getName().isBlank()) {
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
    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(userRepository.get(id));
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userRepository.values());
    }

    @Override
    public List<Long> getUsersId() {
        return new ArrayList<>(userRepository.keySet());
    }

    private long generateId() {
        return ++generatorId;
    }
}

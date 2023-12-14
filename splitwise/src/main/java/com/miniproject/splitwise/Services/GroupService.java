package com.miniproject.splitwise.Services;

import com.miniproject.splitwise.Exceptions.UserNotFoundException;
import com.miniproject.splitwise.Models.Group;
import com.miniproject.splitwise.Models.User;
import com.miniproject.splitwise.Repositories.GroupRepository;
import com.miniproject.splitwise.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    public Group createGroup (String name, String description, Long adminId,
                               List<Long> membersId) {
        Group newGroup = new Group();
        Optional<User> adminOptional = userRepository.findById(adminId);
        if (adminOptional.isEmpty()) {
            throw new UserNotFoundException("Sorry!! No user is there with the given userId." +
                    "Please enter the valid userId.");
        }
        User admin = adminOptional.get();

        List<User> groupMembers = new ArrayList<>();
        groupMembers.add(admin);
        for (Long userId : membersId) {
            Optional<User> userOptional = userRepository.findById(adminId);
            if (userOptional.isEmpty()) {
                throw new UserNotFoundException("Sorry!! No user is there with the given userId." +
                        "Please enter the valid userId.");
            }
            User member = userOptional.get();
            groupMembers.add(member);
        }

        newGroup.setName(name);
        newGroup.setDescription(description);
        newGroup.setCreatedAt(new Date());
        newGroup.setAdmin(admin);
        newGroup.setUsers(groupMembers);
        newGroup.setAmount(0);

        return groupRepository.save(newGroup);
    }

    public List<Group> showGroupsByUser (Long userId) {
        Optional<User> adminOptional = userRepository.findById(userId);
        if (adminOptional.isEmpty()) {
            throw new UserNotFoundException("Sorry!! No user is there with the given userId." +
                    "Please enter the valid userId.");
        }
        User admin = adminOptional.get();
        List<Group> groupsList = groupRepository.findAll();

        List<Group> filteredGroups = new ArrayList<>();
        for(Group group : groupsList) {
            if(group.getAdmin().equals(admin)) {
                filteredGroups.add(group);
            }
        }
        return filteredGroups;
    }
}

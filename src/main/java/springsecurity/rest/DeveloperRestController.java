package springsecurity.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springsecurity.model.Developer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestController {

    private List<Developer> DEVELOPERS = Stream.of(
            new Developer(1, "Ivan", "Ivanov"),
            new Developer(2, "Pavel", "Pavlov"),
            new Developer(3, "Petr", "Petrov")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Developer> allDeveloper() {
        return DEVELOPERS;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('developers:read')")
    public Developer getById(@PathVariable long id) {
        return DEVELOPERS.stream().filter(developer -> Objects.equals(developer.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public Developer create(@RequestBody Developer developer) {
        this.DEVELOPERS.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public void deleteById(@PathVariable long id) {
        this.DEVELOPERS.removeIf(developer -> Objects.equals(developer.getId(), id));
    }
}

package com.tms.speeding.domain.dbo;

import com.tms.speeding.repository.RankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RankDboTest {

    @Autowired
    private RankRepository repo;

    private RankDbo rank;

    @BeforeEach
    public void setUp() {
        this.rank = new RankDbo();
        this.rank.setTitle("TestTitle");
    }

    @Test
    public void createRankTest() {
        repo.save(this.rank);

        Optional<RankDbo> findingRank = repo.findById(this.rank.getId());

        if (findingRank.isPresent()) {
            assertNotNull(findingRank.get());
            assertThat(findingRank.get().getId()).isEqualTo(this.rank.getId());
            assertThat(findingRank.get().getTitle()).isEqualTo(this.rank.getTitle());
        }
    }

    @Test
    public void addInspectorListTest() {
        InspectorDbo firstInspectorWithThisRank = new InspectorDbo(
                new PersonDbo("First", "Surname1", new Date()));
        InspectorDbo secondInspectorWithThisRank = new InspectorDbo(
                new PersonDbo("Second", "Surname2", new Date()));

        firstInspectorWithThisRank.setRank(this.rank);
        secondInspectorWithThisRank.setRank(this.rank);

        List<InspectorDbo> inspectorList = new ArrayList<>();
        inspectorList.add(firstInspectorWithThisRank);
        inspectorList.add(secondInspectorWithThisRank);

        this.rank.setInspectors(inspectorList);

        repo.save(this.rank);

        Optional<RankDbo> findingRank = repo.findByTitle(this.rank.getTitle());
        if (findingRank.isPresent()) {
            RankDbo findingRankDbo = findingRank.get();

            assertThat(findingRankDbo.getId()).isEqualTo(this.rank.getId());

            assertThat(findingRankDbo.getInspectors().get(0)).isNotEqualTo(secondInspectorWithThisRank);
            assertThat(findingRankDbo.getInspectors().get(1)).isNotEqualTo(firstInspectorWithThisRank);
            assertThat(findingRankDbo.getInspectors().get(0)).isEqualTo(this.rank.getInspectors().get(0));
            assertThat(findingRankDbo.getInspectors().get(1)).isEqualTo(this.rank.getInspectors().get(1));
        }
    }

    @Test
    public void deleteRankTest() {
        repo.save(this.rank);

        repo.deleteById(this.rank.getId());

        Optional<RankDbo> findingRank = repo.findByTitle(this.rank.getTitle());
        findingRank.ifPresent(rankDbo -> assertThat(rankDbo).isNotEqualTo(this.rank));

    }

}
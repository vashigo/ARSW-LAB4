/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts01 = new Point[]{new Point(140, 140), new Point(115, 115)};
        Point[] pts02 = new Point[]{new Point(141, 141), new Point(116, 116)};
        Point[] pts03 = new Point[]{new Point(142, 142), new Point(117, 117)};
        Point[] pts04 = new Point[]{new Point(143, 143), new Point(116, 116)};

        Blueprint bp01 = new Blueprint("Camilo", "bp01", pts01);
        Blueprint bp02 = new Blueprint("Camilo", "bp02", pts02);
        Blueprint bp03 = new Blueprint("Andres", "bp03", pts03);
        Blueprint bp04 = new Blueprint("Alex", "bp04", pts04);

        blueprints.put(new Tuple<>(bp01.getAuthor(), bp01.getName()), bp01);
        blueprints.put(new Tuple<>(bp02.getAuthor(), bp02.getName()), bp02);
        blueprints.put(new Tuple<>(bp03.getAuthor(), bp03.getName()), bp03);
        blueprints.put(new Tuple<>(bp04.getAuthor(), bp04.getName()), bp04);

    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintsSet = new HashSet<>();
        for (Tuple<String, String> x : blueprints.keySet()) {
            if (author.equals(x.getElem1())) {
                blueprintsSet.add(blueprints.get(x));
            }
        }
        return blueprintsSet;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> bpsToReturn = new HashSet<>();
        for (Tuple<String, String> x : blueprints.keySet()) {
            bpsToReturn.add(blueprints.get(x));
        }
        return bpsToReturn;
    }

}

package am.test.service;

import am.test.model.FemaleName;
import am.test.model.FemaleSurname;
import am.test.model.MaleName;
import am.test.model.MaleSurname;
import am.test.repository.FemaleNameRepository;
import am.test.repository.FemaleSurnameRepository;
import am.test.repository.MaleNameRepository;
import am.test.repository.MaleSurnameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by
 * Mher Petrosyan
 * Email mher13.02.94@gmail.ru
 */
@Service
public class CompareService {

    private final FemaleNameRepository femaleNameRepository;
    private final FemaleSurnameRepository femaleSurnameRepository;
    private final MaleNameRepository maleNameRepository;
    private final MaleSurnameRepository maleSurnameRepository;

    @Autowired
    public CompareService(
            FemaleNameRepository femaleNameRepository,
            FemaleSurnameRepository femaleSurnameRepository,
            MaleNameRepository maleNameRepository,
            MaleSurnameRepository maleSurnameRepository
    ) {
        this.femaleNameRepository = femaleNameRepository;
        this.femaleSurnameRepository = femaleSurnameRepository;
        this.maleNameRepository = maleNameRepository;
        this.maleSurnameRepository = maleSurnameRepository;
    }
    public void saveMaleName(String[] names) {
        for (String name : names) {
            MaleName maleName = new MaleName();
            maleName.setName(name);
            maleNameRepository.save(maleName);
        }
    }

    public void saveFemaleName(String[] names) {
        for (String name : names) {
            FemaleName femaleName = new FemaleName();
            femaleName.setName(name);
            femaleNameRepository.save(femaleName);
        }
    }

    public void saveFemaleSurname(String[] surnames) {
        for (String surname : surnames) {
            FemaleSurname femaleSurname = new FemaleSurname();
            femaleSurname.setSurname(surname);
            femaleSurnameRepository.save(femaleSurname);
        }
    }

    public void saveMaleSurname(String[] surnames) {
        for (String surname : surnames) {
            MaleSurname maleSurname = new MaleSurname();
            maleSurname.setSurname(surname);
            maleSurnameRepository.save(maleSurname);
        }
    }
    public List<String> compareName() {
        List<MaleName> maleNames = maleNameRepository.findAll();
        List<String> maleNamesStr = maleNames
                .stream()
                .map(MaleName::getName)
                .collect(Collectors.toList());
        List<FemaleName> femaleNames = femaleNameRepository.findAll();
        List<String> femaleNamesStr = femaleNames
                .stream()
                .map(FemaleName::getName)
                .collect(Collectors.toList());
        maleNamesStr.retainAll(femaleNamesStr);
        return maleNamesStr;
    }

    public List<String> compareSurname() {
        List<MaleSurname> maleSurnames = maleSurnameRepository.findAll();
        List<String> maleSurnamesStr = maleSurnames
                .stream()
                .map(MaleSurname::getSurname)
                .collect(Collectors.toList());
        List<FemaleSurname> femaleSurnames = femaleSurnameRepository.findAll();
        List<String> femaleSurnamesStr = femaleSurnames
                .stream()
                .map(FemaleSurname::getSurname)
                .collect(Collectors.toList());
        maleSurnamesStr.retainAll(femaleSurnamesStr);
        return maleSurnamesStr;
    }
}

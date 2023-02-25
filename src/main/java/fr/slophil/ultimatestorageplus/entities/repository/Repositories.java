package fr.slophil.ultimatestorageplus.entities.repository;

public enum Repositories {

    PLACED_STORAGE(new PlacedStorageRepository()),

    ;

    private final Repository<?> repository;

    <T> Repositories(Repository<T> repository) {
        this.repository = repository;
    }

    public Repository<?> getRepository() {
        return repository;
    }
}

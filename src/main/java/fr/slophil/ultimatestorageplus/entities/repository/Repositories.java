package fr.slophil.ultimatestorageplus.entities.repository;

public enum Repositories {

    PLACED_STORAGE(new PlacedStorageRepository()),

    ;

    private final Repository<?> repository;

    /**
     * Constructor
     *
     * @param repository Repository
     */
    <T> Repositories(Repository<T> repository) {
        this.repository = repository;
    }

    /**
     * Get the repository
     *
     * @return Repository
     */
    public Repository<?> getRepository() {
        return repository;
    }
}

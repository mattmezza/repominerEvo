package net.sf.jeasyorm;

public interface NameGuesser {
    
    public String[] guessTableName(Class<?> entityClass);
    
    public String[] guessColumnName(Class<?> entityClass, String field);

}

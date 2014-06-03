package net.sf.jeasyorm;

public class SimpleNameGuesser implements NameGuesser {

    @Override
    public String[] guessTableName(Class<?> entityClass) {
        String name1 = entityClass.getSimpleName();
        String name2 = name1.replaceAll("([a-z])([A-Z])", "$1_$2");
        if (name1.equals(name2)) {
            return new String[] {
                    name1.toUpperCase(),
                    "T" + name1.toUpperCase(),
            };
        } else {
            return new String[] {
                    name1.toUpperCase(),
                    name2.toUpperCase(),
                    "T" + name1.toUpperCase(),
                    "T" + name2.toUpperCase()
            };
        }
    }

    @Override
    public String[] guessColumnName(Class<?> entityClass, String field) {
        String name1 = field;
        String name2 = name1.replaceAll("([a-z])([A-Z])", "$1_$2");
        if (name1.equals(name2)) {
            return new String[] {
                    name1.toUpperCase()
            };
        } else {
            return new String[] {
                    name1.toUpperCase(),
                    name2.toUpperCase()
            };
        }
    }

}

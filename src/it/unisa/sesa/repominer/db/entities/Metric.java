package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "metrics")
public class Metric {

	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;

	public static final String BCCM_DESCRIPTION="System entropy calculated by the Basic Code Change Model";
	public static final String BCCM_NAME="BCC_Model";
	public static final String NAUTH_NAME = "NAUTH";
	public static final String NAUTH_DESCRIPTION = "Number of authors of changes made on a package";
	public static final String REVISION_NAME = "NR";
	public static final String REVISION_DESCRIPTION = "Number of revision of the system";
	public static final String CHANGE_SET_SIZE_NAME = "mean_CHANGE_SET_SIZE";
	public static final String CHANGE_SET_SIZE_DESCRIPTION = "Mean dimension of modified files of a package";
	public static final String NCHANGE_NAME = "mean_NCHANGE";
	public static final String NCHANGE_DESCRIPTION = "Mean number of time in which files of a package have been modified";
	public static final String NREF_NAME = "mean_NREF";
	public static final String NREF_DESCRIPTION = "Mean number of time in which files of a package have been refactored";
	public static final String NFIX_NAME = "mean_NFIX";
	public static final String NFIX_DESCRIPTION = "Mean number of time in which files of a package have been fixed for a bug";
	public static final String SUM_LINES_NAME = "Sum_LINES";
	public static final String SUM_LINES_DESCRIPTION = "Sum of all the insertions or deletions made on the files of a package";
	public static final String MEAN_LINES_NAME = "Mean_LINES";
	public static final String MEAN_LINES_DESCRIPTION = "Mean number of insertions or deletions made on the files of a package";
	public static final String MAX_LINES_NAME = "Max_LINES";
	public static final String MAX_LINES_DESCRIPTION = "Maximum number of insertions or deletions made on the files of a package";
	public static final String ECCM_NAME = "ECC_Model";
	public static final String ECCM_DESCRIPTION = "System entropy calculated by the Extended Code Change Model";
	public static final String EECM_STATIC_DESCRIPTION = "System entropy calculated by the Extended Code Change Model using Normalized Static Entropy";
	public static final String EECM_STATIC_NAME = "ECC_Static_Model";
	public static final String NUM_REVISION_NAME = "Num_Rev";
	public static final String NUM_REVISION_DESCRIPTION = "Number of revision of the system";
	
	public Metric(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Metric() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Metric [id=" + id + ", name=" + name + ", description="
				+ description + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Metric other = (Metric) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

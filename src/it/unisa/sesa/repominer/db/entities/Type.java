package it.unisa.sesa.repominer.db.entities;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "types")
public class Type {

	@Column(name = "id")
	private Integer id;
	@Column(name = "inport_id")
	private Integer importId;
	@Column(name = "lines_number")
	private Integer linesNumber;
	@Column(name = "src_file_location")
	private String srcFileLocation;
	@Column(name = "header_file_location")
	private String headerFileLocation;
	@Column(name = "source_conteiner")
	private Integer sourceContainer;

	public Type(Integer id, Integer importId, Integer linesNumber,
			String srcFileLocation, String headerFileLocation,
			Integer sourceContainer) {
		this.id = id;
		this.importId = importId;
		this.linesNumber = linesNumber;
		this.srcFileLocation = srcFileLocation;
		this.headerFileLocation = headerFileLocation;
		this.sourceContainer = sourceContainer;
	}

	public Type() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getImportId() {
		return importId;
	}

	public void setImportId(Integer importId) {
		this.importId = importId;
	}

	public Integer getLinesNumber() {
		return linesNumber;
	}

	public void setLinesNumber(Integer linesNumber) {
		this.linesNumber = linesNumber;
	}

	public String getSrcFileLocation() {
		return srcFileLocation;
	}

	public void setSrcFileLocation(String srcFileLocation) {
		this.srcFileLocation = srcFileLocation;
	}

	public String getHeaderFileLocation() {
		return headerFileLocation;
	}

	public void setHeaderFileLocation(String headerFileLocation) {
		this.headerFileLocation = headerFileLocation;
	}

	public Integer getSourceContainer() {
		return sourceContainer;
	}

	public void setSourceContainer(Integer sourceContainer) {
		this.sourceContainer = sourceContainer;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", importId=" + importId + ", linesNumber="
				+ linesNumber + ", srcFileLocation=" + srcFileLocation
				+ ", headerFileLocation=" + headerFileLocation
				+ ", sourceContainer=" + sourceContainer + "]";
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
		Type other = (Type) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

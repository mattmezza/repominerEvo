package it.unisa.sesa.repominer.db.entities;

import java.util.Date;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name = "review")
public class Review {

	@Column(name = "versioning_url")
	private String versioningUrl;
	@Column(name = "name_app")
	private String nameApp;
	@Column(name = "author")
	private String author;
	@Column(name = "title")
	private String title;
	@Column(name = "review")
	private String review;
	@Column(name = "rating")
	private String rating;
	@Column(name = "date")
	private Date date;

	public Review(String versioningUrl, String nameApp, String author,
			String title, String review, String rating, Date date) {
		this.versioningUrl = versioningUrl;
		this.nameApp = nameApp;
		this.author = author;
		this.title = title;
		this.review = review;
		this.rating = rating;
		this.date = date;
	}

	public Review() {
	}

	public String getVersioningUrl() {
		return versioningUrl;
	}

	public void setVersioningUrl(String versioningUrl) {
		this.versioningUrl = versioningUrl;
	}

	public String getNameApp() {
		return nameApp;
	}

	public void setNameApp(String nameApp) {
		this.nameApp = nameApp;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Review [versioningUrl=" + versioningUrl + ", nameApp="
				+ nameApp + ", author=" + author + ", title=" + title
				+ ", review=" + review + ", rating=" + rating + ", date="
				+ date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((nameApp == null) ? 0 : nameApp.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((review == null) ? 0 : review.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((versioningUrl == null) ? 0 : versioningUrl.hashCode());
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
		Review other = (Review) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (nameApp == null) {
			if (other.nameApp != null)
				return false;
		} else if (!nameApp.equals(other.nameApp))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (review == null) {
			if (other.review != null)
				return false;
		} else if (!review.equals(other.review))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (versioningUrl == null) {
			if (other.versioningUrl != null)
				return false;
		} else if (!versioningUrl.equals(other.versioningUrl))
			return false;
		return true;
	}

}

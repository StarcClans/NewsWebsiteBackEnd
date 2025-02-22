package lt.ca.javau11.entity;

//import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"uri"})})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String section;
    private String slugName;
    private String subsection;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String abstractText;
    private String uri;
    private String url;
    private String byline;
    
	private String source;
    private String itemType;
    private String firstPublishedDate;
    private String subheadline;
    private String updatedDate;
    private String createdDate;
    private String publishedDate;
    private String materialTypeFacet;
    private String kicker;
    private String headline;
    
    private String shortUrl;
    private String thumbnailStandard;
   

	@Column(columnDefinition = "TEXT", length = 4000) //Adjust Length
	private String desFacet;
	@Column(columnDefinition = "TEXT", length = 4000) //Adjust Length
	private String orgFacet;
	@Column(columnDefinition = "TEXT", length = 4000) //Adjust Length
	private String perFacet;
	@Column(columnDefinition = "TEXT", length = 4000) //Adjust Length
	private String geoFacet;
    private String blogName;
    @Column(columnDefinition = "TEXT")
    private String relatedUrls;
    @Column(columnDefinition = "TEXT")
    private String multimedia;
    
    public String getByline() {
		return byline;
	}
	public void setByline(String byline) {
		this.byline = byline;
	}
	public String getDesFacet() {
		return desFacet;
	}
	public void setDesFacet(String desFacet) {
		this.desFacet = desFacet;
	}
	public String getOrgFacet() {
		return orgFacet;
	}
	public void setOrgFacet(String orgFacet) {
		this.orgFacet = orgFacet;
	}
	public String getPerFacet() {
		return perFacet;
	}
	public void setPerFacet(String perFacet) {
		this.perFacet = perFacet;
	}
	public String getGeoFacet() {
		return geoFacet;
	}
	public void setGeoFacet(String geoFacet) {
		this.geoFacet = geoFacet;
	}
	public String getRelatedUrls() {
		return relatedUrls;
	}
	public void setRelatedUrls(String relatedUrls) {
		this.relatedUrls = relatedUrls;
	}
	public String getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(String multimedia) {
		this.multimedia = multimedia;
	}

    
    public String getThumbnailStandard() {
		return thumbnailStandard;
	}
	public void setThumbnailStandard(String thumbnailStandard) {
		this.thumbnailStandard = thumbnailStandard;
	}
    public String getBlogName() {
		return blogName;
	}
	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}
    public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
    
    public String getSlugName() {
  		return slugName;
  	}

  	public void setSlugName(String slugName) {
  		this.slugName = slugName;
  	}

  	public String getSource() {
  		return source;
  	}

  	public void setSource(String source) {
  		this.source = source;
  	}

  	public String getFirstPublishedDate() {
  		return firstPublishedDate;
  	}

  	public void setFirstPublishedDate(String firstPublishedDate) {
  		this.firstPublishedDate = firstPublishedDate;
  	}

  	public String getSubheadline() {
  		return subheadline;
  	}

  	public void setSubheadline(String subheadline) {
  		this.subheadline = subheadline;
  	}

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getMaterialTypeFacet() {
        return materialTypeFacet;
    }

    public void setMaterialTypeFacet(String materialTypeFacet) {
        this.materialTypeFacet = materialTypeFacet;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
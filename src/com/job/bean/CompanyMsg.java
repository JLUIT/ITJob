package com.job.bean;

public class CompanyMsg {

	private String job_name;
	private String company_name;
	private String experience;
	private String site;
	private String company_size;
	private String salary;
	private String company_property;
	private String send_date;

	
	
	public CompanyMsg(String job_name, String company_name, String experience,
			String site, String company_size, String salary,
			String company_property, String send_date) {
		super();
		this.job_name = job_name;
		this.company_name = company_name;
		this.experience = experience;
		this.site = site;
		this.company_size = company_size;
		this.salary = salary;
		this.company_property = company_property;
		this.send_date = send_date;
	}

	
	
	public CompanyMsg() {
		super();
	}



	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getCompany_size() {
		return company_size;
	}

	public void setCompany_size(String company_size) {
		this.company_size = company_size;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getSend_date() {
		return send_date;
	}

	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}

	public String getCompany_property() {
		return company_property;
	}

	public void setCompany_property(String company_property) {
		this.company_property = company_property;
	}


}

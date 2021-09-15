# Spring-Boot-DTO
implementing many to one relationship by using DTO design pattern 

Concepts used in the Project are
  Spring boot Rest
  Spring boot JPA
  DTO Design patern 
  
  From this project we can undersand and using of DTO design pattern 
  
  
  The Project contains two model classes Users and Locations. Here Many Users belong to one location and one location have many users So we need to implement Many to one relationship between them.
  
  
  **Model classes:**
  
  ```
  package com.dto.app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
public class Users {
	@Id
	private long uid;
	private String email;
	private String fname;
	private String lname;
	private String password;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id",nullable=false)
	private Locations location;

	public long getId() {
		return uid;
	}

	public void setId(long id) {
		this.uid = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Locations getLocation() {
		return location;
	}

	public void setLocation(Locations location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Users [id=" + uid + ", email=" + email + ", fname=" + fname + ", lname=" + lname + ", password="
				+ password + ", location=" + location + "]";
	}

}

  ```
  
  ```
  package com.dto.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
public class Locations {
	@Id
	private long lid;
	private String place;
	private String description;
	private double longitude;
	private double latitude;

	public long getId() {
		return lid;
	}

	public void setId(long id) {
		this.lid = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Locations [id=" + lid + ", place=" + place + ", description=" + description + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}

}

  
  ```
  
  **Service-Interfacs**
  
  ```
  package com.dto.app.service;

import com.dto.app.model.Locations;

public interface locationservice {


	public Locations save(Locations location);
	public Locations getlocationByid(long lid);
}

  ```
```
package com.dto.app.service;

import java.util.List;

import com.dto.app.dto.UserLocationDTO;
import com.dto.app.model.Users;

public interface Userservice {

	public Users save(Users user);
	public List<UserLocationDTO> getAllUserLocations();
}

```
**Repository interfaces**
```
package com.dto.app.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.dto.app.model.Users;

public interface userRepository extends JpaRepository<Users, Long> {

	
}

```
```
package com.dto.app.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.dto.app.model.Locations;

public interface LocationRepository extends JpaRepository<Locations, Long> {


}

```

**Service-Implementation**

```
package com.dto.app.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.dto.app.dto.UserLocationDTO;
import com.dto.app.model.Users;
import com.dto.app.repo.userRepository;
import com.dto.app.service.Userservice;

@Service
public class UserServiceimpl implements Userservice{
	@Autowired
	private userRepository repo;

	public List<UserLocationDTO> getAllUserLocations(){
		return repo.findAll()
				.stream()
				.map(this::covertEntryToDto)
				.collect(Collectors.toList());
	}
	
	private UserLocationDTO covertEntryToDto(Users user) {
		UserLocationDTO userLocationDTO=new UserLocationDTO();
		userLocationDTO.setUserId(user.getId());
		userLocationDTO.setEmail(user.getEmail());
		userLocationDTO.setPlace(user.getLocation().getPlace());
		userLocationDTO.setLatitude(user.getLocation().getLatitude());
		userLocationDTO.setLongitude(user.getLocation().getLongitude());
		return userLocationDTO;
	}
	
	@Modifying //non-select operation
	@Transactional //service Layer
	public Users save( Users user) {
		user=repo.save(user);
		return user;
	}
	
	
}

```

```
package com.dto.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.app.model.Locations;
import com.dto.app.repo.LocationRepository;
import com.dto.app.service.locationservice;
@Service
public class locationserviceimpl implements locationservice {

	@Autowired
	private LocationRepository lrepo;
	@Override
	public Locations save(Locations location) {
		location=lrepo.save(location);
		return location;
	}

	@Override
	public Locations getlocationByid(long lid) {
		Locations location=lrepo.findById(lid).get();
		return location;
	}

}


```

**DTO Classes**

```
package com.dto.app.dto;

import lombok.Data;

@Data
public class UserLocationDTO {
	private long userId;
	private String email;
	private String place;
	private double longitude;
	private double latitude;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long l) {
		this.userId = l;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "UserLocationDTO [userId=" + userId + ", email=" + email + ", place=" + place + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}

}

```

```
package com.dto.app.dto;


public class SaveUsersLocDto {
	private long lid;
	private long uid;
	private String email;
	private String fname;
	private String lname;
	private String password;
	public long getLid() {
		return lid;
	}
	public void setLid(long lid) {
		this.lid = lid;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UsersLocationDto [lid=" + lid + ", uid=" + uid + ", email=" + email + ", fname=" + fname + ", lname="
				+ lname + ", password=" + password + "]";
	}
	
}

```

**Controller class**
```
package com.dto.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.app.dto.UserLocationDTO;
import com.dto.app.dto.SaveUsersLocDto;
import com.dto.app.model.Locations;
import com.dto.app.model.Users;
import com.dto.app.serviceimpl.UserServiceimpl;
import com.dto.app.serviceimpl.locationserviceimpl;

@RestController
@RequestMapping("/dto")
public class Controller {
	@Autowired
	private locationserviceimpl locservice;
	@Autowired
	private UserServiceimpl impuserser;
	@GetMapping("/locations")
	public List<UserLocationDTO> getAllUserLocations(){
		return impuserser.getAllUserLocations();
	}
	
	
	
	@PostMapping("/save")
	public Users saveUser(@RequestBody SaveUsersLocDto userdto) {
		Locations loc=locservice.getlocationByid(userdto.getLid());
		
		Users user=new Users();
		user.setId(userdto.getUid());
		user.setEmail(userdto.getEmail());
		user.setFname(userdto.getFname());
		user.setLname(userdto.getLname());
		user.setLocation(loc);
		user.setPassword(userdto.getPassword());
		return impuserser.save(user);
	}
	
	@PostMapping("/saveloc")
	public long save(@RequestBody Locations location) {
		location=locservice.save(location);
		return location.getId();
	}
	
}

```

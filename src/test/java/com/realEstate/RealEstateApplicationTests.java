package com.realEstate;

import com.realEstate.entity.District;
import com.realEstate.service.DistrictService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class RealEstateApplicationTests {

	@Autowired
	DistrictService districtService;

	@Test
	void saveTest() {
		District district = new District();
		district.setName("New district");
		district = districtService.save(district);

		Assert.notNull(district,"Null object");
		Assert.isTrue(district.equals(districtService.findById(district.getId())),"Null object");
	}

	@Test
	void findAll(){
		int firstSize = districtService.findAll().size();
		District district1 = new District();
		district1.setName("New district");
		districtService.save(district1);
		District district2 = new District();
		district2.setName("New district");
		districtService.save(district2);

		int currentSize = districtService.findAll().size();

		Assert.isTrue(firstSize==(currentSize-2),"Incorrect find all method");
	}

	@Test
	void deleteTest() {
		District district = new District();
		district.setName("New district");
		district = districtService.save(district);
		districtService.deleteByID(district.getId());

		Assert.isNull(districtService.findById(district.getId()),"Not deleted object");
	}
}

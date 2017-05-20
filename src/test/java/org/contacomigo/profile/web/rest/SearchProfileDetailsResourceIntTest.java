package org.contacomigo.profile.web.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.contacomigo.profile.ProfileApp;
import org.contacomigo.profile.config.SecurityBeanOverrideConfiguration;
import org.contacomigo.profile.domain.Address;
import org.contacomigo.profile.domain.ProfileDetails;
import org.contacomigo.profile.service.AddressService;
import org.contacomigo.profile.service.ProfileDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProfileApp.class, SecurityBeanOverrideConfiguration.class})
public class SearchProfileDetailsResourceIntTest {

    @Autowired
    private ProfileDetailsService profileDetailsService;

    @Autowired
    private AddressService addressService;

    private List<ProfileDetails> profileDetails;
    private List<Address> addresses;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    public List<Address> createAddresses() {
    	List<Address> result = new ArrayList<Address>();

		result.add(new Address()
			.profileId(UUID.randomUUID().toString().replaceAll("-", ""))
			.street("Av vaz ferreira").city("Tupancireta").country("Brasil"));
		result.add(new Address()
			.profileId(UUID.randomUUID().toString().replaceAll("-", ""))
			.street("Felicissimo de azevedo").number("234").city("Porto alegre").country("Brasil"));
		result.add(new Address()
			.profileId(UUID.randomUUID().toString().replaceAll("-", ""))
			.street("Felicissimo de azevedo").number("182").city("Porto alegre").country("Brasil"));

        return result;
    }

    public List<ProfileDetails> createProfileDetails(List<Address> addresses) {
    	List<ProfileDetails> result = new ArrayList<ProfileDetails>();

    	addresses.forEach(address -> {
    		result.add(new ProfileDetails()
				.profileId(address.getProfileId())
				.detailId(UUID.randomUUID().toString().replaceAll("-", "")));
    	});

        return result;
    }

    @Before
    public void initTest() {
    	addressService.deleteAll();
    	profileDetailsService.deleteAll();
    	addresses = createAddresses();
        profileDetails = createProfileDetails(addresses);

        addresses = addressService.save(addresses);
        profileDetails = profileDetailsService.save(profileDetails);
    }

    @Test
    public void findeAllByAddress() throws Exception {
    	List<ProfileDetails> result = profileDetailsService.findByAddress("brasil");
    	assertThat(result).hasSize(3);

    	result = profileDetailsService.findByAddress("Porto alegre");
    	assertThat(result).hasSize(2);

    	result = profileDetailsService.findByAddress("av vaz ferreira");
    	assertThat(result).hasSize(1);
    }
}

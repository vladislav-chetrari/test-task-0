package com.example.testtask0.data.network.api.client

import com.example.testtask0.data.network.api.Api
import com.example.testtask0.ui.model.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsClient @Inject constructor(private val api: Api) {

    private val randomImageLinks = listOf(
        "https://i.picsum.photos/id/20/600/600.jpg?hmac=91maC538H_TtZ-ACJhdrP5T_cUKuZsCkSEXkbHLjvLA",
        "https://i.picsum.photos/id/127/600/600.jpg?hmac=km47e-dKPYG-1vheoEz3WlbUrtSrYoeJ7Aft7UYbm_M",
        "https://i.picsum.photos/id/183/600/600.jpg?hmac=JRIkl4m2tWTzD6w5jHm9l0ayN4lJE4BhUnxf8J_cj0A",
        "https://i.picsum.photos/id/989/600/600.jpg?hmac=y2H3p_6IUICrOB52uoOVsgWYL5lsMZaaS4mQoZ-y4oQ",
        "https://i.picsum.photos/id/11/600/600.jpg?hmac=OoD3fF4hlAiZhgA6M9z_7Db_Mrhp4TKDfsWgx_7mYbI",
        "https://i.picsum.photos/id/845/600/600.jpg?hmac=csTkHrpkkzsOsMb0aLWWMBKNczp6_PHK50VPBFb_p84",
        "https://i.picsum.photos/id/1006/600/600.jpg?hmac=BKGVDYxDU4NAKE6eca7lrsiGWzkS2ejOhDGEO8S_iO8",
        "https://i.picsum.photos/id/723/600/600.jpg?hmac=SZz9YeQOA-Y2gnBBw9nKsEOs0aKeBk598PPHdvYlLLA",
        "https://i.picsum.photos/id/958/600/600.jpg?hmac=6aQCup0gPCKR0qMf5SiYoq33H1S9bjrgDM4CWc7YhEs",
        "https://i.picsum.photos/id/388/600/600.jpg?hmac=4hCtk6YpSVUOhlviMYbdq3Z3aFcPvRzg2mSp5OQ2I30"
    )

    fun locations(page: Int): List<Location> = api.locations(page).body.locations.mapIndexed { index, l ->
        Location(l.id, l.name, l.country, randomImageLinks[index % randomImageLinks.size], l.latitude, l.longitude)
    }
}
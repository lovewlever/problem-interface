package com.problem.pl.model.services

import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectSystemDevicesEntity
import org.springframework.stereotype.Service

@Service
interface ProjectProblemService {

    fun querySystemDevicesList(): ResultPro<TProjectSystemDevicesEntity>

}
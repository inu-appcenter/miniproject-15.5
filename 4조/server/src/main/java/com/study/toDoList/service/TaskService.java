package com.study.toDoList.service;

import com.study.toDoList.domain.Member;
import com.study.toDoList.domain.Task;
import com.study.toDoList.dto.TaskListResponseDto;
import com.study.toDoList.dto.TaskResponseDto;
import com.study.toDoList.dto.TaskSaveDto;
import com.study.toDoList.dto.TaskUpdateDto;
import com.study.toDoList.exception.ex.MyErrorCode;
import com.study.toDoList.exception.ex.MyNotFoundException;
import com.study.toDoList.exception.ex.MyNotPermittedException;
import com.study.toDoList.repositoy.MemberRepository;
import com.study.toDoList.repositoy.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Long id,TaskSaveDto taskSaveDto){
        Member member = memberRepository.findById(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.USER_NOT_FOUND));
        return taskRepository.save(Task.builder().member(member).title(taskSaveDto.getTitle()).description(taskSaveDto.getDescription()).endDate(taskSaveDto.getEndDate()).build()).getId();
    }

    @Transactional
    public Long update(Long id, TaskUpdateDto taskUpdateDto, Long loginId){
        Task task =taskRepository.findById(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.TASK_NOT_FOUND));
        if(task.getMember().getId()!=loginId){
            throw new MyNotPermittedException(MyErrorCode.UPDATE_NOT_PERMITTED);
        }
        task.update(taskUpdateDto.getTitle(),taskUpdateDto.getDescription(),taskUpdateDto.getEndDate());
        return id;
    }

    @Transactional
    public void delete(Long id,Long loginId){
        Task task =taskRepository.findById(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.TASK_NOT_FOUND));
        if(task.getMember().getId()!=loginId){
            throw new MyNotPermittedException(MyErrorCode.DELETE_NOT_PERMITTED);
        }
        taskRepository.delete(task);
    }

    @Transactional(readOnly = true)
    public List<TaskListResponseDto> getAllTask(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.USER_NOT_FOUND));
        return taskRepository.findAllByMember(member).stream().map(TaskListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTask(Long id){
        Task task =taskRepository.findById(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.TASK_NOT_FOUND));
        return new TaskResponseDto(task);
    }

    @Transactional
    public TaskResponseDto changeIsFinished(Long id, Long loginId){
        Task task =taskRepository.findById(id).orElseThrow(()-> new MyNotFoundException(MyErrorCode.TASK_NOT_FOUND));
        if(task.getMember().getId()!=loginId){
            throw new MyNotPermittedException(MyErrorCode.DELETE_NOT_PERMITTED);
        }
        task.changeIsFinished();
        return new TaskResponseDto(task);
    }

}

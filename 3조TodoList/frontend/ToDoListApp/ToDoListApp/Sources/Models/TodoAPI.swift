//
//  TodoAPI.swift
//  ToDoListApp
//
//  Created by 이대현 on 11/29/23.
//

import Foundation
import Moya
import UIKit

let USERID = 1 // 해당 id에 해당하는 유저의 todolist 불러옴
enum TodoAPI {
    // Todo 정보 API
    case getDetail(_ id: Int)
    case updateTodo(_ id: Int, _ params: TodoRequestDTO)
    case createTodo(_ params: TodoRequestDTO)
    case updateCheck(_ id: Int, _ completed: Bool)
    case deleteTodo(_ id: Int)
    // 유저 정보 API
    case getList
    case login(_ id: String, _ password: String)
}

extension TodoAPI: TargetType {
    
    var baseURL: URL  {
        return URL(string: "http://hyeongjun.na2ru2.me")!
    }
    
    var path: String {
        switch self {
        case .getList:
            return "/users/search"
        case .createTodo(_):
            return "/todos"
        case .updateCheck(_, _):
            return "/todos/check"
        case .deleteTodo(let id),
            .getDetail(let id),
            .updateTodo(let id, _):
            return "/todos/\(id)"
        case .login(_, _):
            return "/users/sign-in"
        }
    }
    
    var method: Moya.Method {
        switch self {
        case .getList, .getDetail(_):
            return .get
        case .createTodo(_), .login(_, _):
            return .post
        case .updateCheck(_, _), .updateTodo(_, _):
            return .put
        case .deleteTodo(_):
            return .delete
        }
    }
    
    var task: Moya.Task {
        switch self {
        case .getList:
            return .requestPlain
        case .createTodo(let params):
            return .requestJSONEncodable(params)
        case .updateCheck(let id, let completed):
            let params: [String: Any] = [
                "id": id,
                "completed": completed
            ]
            return .requestParameters(parameters: params, encoding: URLEncoding.queryString)
        case .deleteTodo(_):
            return .requestPlain
        case .getDetail(_):
            return .requestPlain
        case .updateTodo(_, let params):
            return .requestJSONEncodable(params)
        case .login(let id, let password):
            let params: [String: Any] = [
                "id": id,
                "password": password
            ]
            return .requestParameters(parameters: params, encoding: URLEncoding.queryString)
        }
    }
    
    var headers: [String : String]? {
        return [
            "Content-type": "application/json",
            "JWT" : (UIApplication.shared.delegate as? AppDelegate)?.TOKEN ?? ""
        ]
    }
}

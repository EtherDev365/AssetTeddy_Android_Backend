<?php

namespace App\Http\Requests\Auth;

use Illuminate\Foundation\Http\FormRequest;

class RegisterRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'name' => 'required',
            'gender' => 'required',
            'contact_number' => 'required',
            'address' => 'required',
            'username'                 => 'required|username|unique:users,username',
            'email'                 => 'required|email|unique:users,email',
            'username'              => 'required|max:20',
            'role'                 => 'nullable|max:20',
            'password'              => 'required|string|min:6',
        ];
    }
}
